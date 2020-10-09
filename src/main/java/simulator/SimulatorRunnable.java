package simulator;

import command.model.State;
import command.model.ElevatorModel;

/**
 * Le coeur du simulateur, met à jour la position en fonction de l'état et de l'écoulement du temps.
 */
class SimulatorRunnable implements Runnable {
    /**
     * La durée en ms entre chaque étage.
     */
    private final double duration;
    /**
     * Le modèle lié à l'ascenseur simulé.
     */
    private final ElevatorModel model;
    /**
     * Vrai tant que la méthode {@link #stop} n'est pas appelée.
     */
    private boolean running = true;

    /**
     * Vrai si l'ascenseur est actuellement en mode STOP_NEXT.
     */
    private boolean isGoingToStop = false;

    /**
     * Le temps écoulé entre le dernier traitement et celui en cours.
     */
    private long elapsed;
    /**
     * Le prochain étage où s'arrêter si en mode STOP_NEXT.
     */
    private int nextFloor = -1;

    /**
     * @param timePerFloor nombre de seconde pour un étage
     * @param model le modèle contenant les données de l'ascenseur simulé
     */
    public SimulatorRunnable(int timePerFloor, ElevatorModel model) {
        this.model = model;
        this.duration = timePerFloor * 1000;
    }

    /**
     * Met la valeur de {@link #running} à faux.
     * Permet de quitter proprement le thread qui contient cette instance.
     */
    public void stop() {
        this.running = false;
    }

    /**
     * @return vrai si en cours d'exécution.
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Fonction appelé dans le Thread.
     * Coeur du simulateur, ce charge de changer la position en fonction de l'état et du temps écoulé
     * depuis le dernier traitement.
     */
    @Override
    public void run() {
        long last = System.currentTimeMillis();
        long current;
        while (running) {
            current = System.currentTimeMillis();
            elapsed = current - last;
            last = current;

            switch (model.state) {
                case STOPPED:
                case EMERGENCY:
                    // Clear l'état, on ne va pas s'arrêter, on l'est !
                    if (isGoingToStop) isGoingToStop = false;
                    break;
                case MOVING_UP_STOP_NEXT:
                    upAndStopNext();
                    break;
                case MOVING_UP:
                    up();
                    break;
                case MOVING_DOWN_STOP_NEXT:
                    downAndStopNext();
                    break;
                case MOVING_DOWN:
                    down();
                    break;
            }

            try {
                //noinspection BusyWait
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Fonction appelé lorsque l'ascenseur est dans l'état {@link State#MOVING_UP}
     */
    private void up() {
        double pos = model.getPosition() + (elapsed / duration);
        if (pos > model.nbFloor) {
            model.setPosition(model.nbFloor);
            model.setState(State.EMERGENCY);
        } else {
            model.setPosition(pos);
        }
    }

    /**
     * Fonction appelé lorsque l'ascenseur est dans l'état {@link State#MOVING_UP_STOP_NEXT}
     */
    private void upAndStopNext() {
        double pos = model.getPosition();
        if (!isGoingToStop) {
            isGoingToStop = true;
            nextFloor = (int) (pos - (pos % 1)) + 1;
        }

        double nextPos = pos + (elapsed / duration);
        if (nextPos >= nextFloor) {
            isGoingToStop = false;
            model.setPosition(nextFloor);
            model.setState(State.STOPPED);
        } else {
            up();
        }
    }

    /**
     * Fonction appelé lorsque l'ascenseur est dans l'état {@link State#MOVING_DOWN}
     */
    private void down() {
        double pos = model.getPosition() - (elapsed / duration);
        if (pos < 0) {
            model.setPosition(0);
            model.setState(State.EMERGENCY);
        } else {
            model.setPosition(pos);
        }
    }

    /**
     * Fonction appelé lorsque l'ascenseur est dans l'état {@link State#MOVING_DOWN_STOP_NEXT}
     */
    private void downAndStopNext() {
        double pos = model.getPosition();
        if (!isGoingToStop) {
            isGoingToStop = true;
            nextFloor = (int) (pos - (pos % 1));

            // Cas spécifique: si on est arrêté à l'étage x.0 et que l'on veut descendre au suivant
            if ((pos % 1) == 0) {
                nextFloor -= 1;
            }
        }

        double nextPos = pos - (elapsed / duration);
        if (nextPos <= nextFloor) {
            isGoingToStop = false;
            model.setPosition(nextFloor);
            model.setState(State.STOPPED);
        } else {
            down();
        }
    }
}
