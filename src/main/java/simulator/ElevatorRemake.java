package simulator;

import commande.State;
import ui.model.ElevatorModel;

/**
 * Un simulateur d'ascenseur utilisable à des fin de démonstration.
 */
public class ElevatorRemake {
    /**
     * Vitesse de l'ascenseur en second/étage
     */
    private final int speed;
    private final ElevatorModel model;
    private Thread thread;
    private Core core;

    public ElevatorRemake(int nbFloor, int speed) {
        this.model = new ElevatorModel();
        this.model.state = State.STOPPED;
        this.model.nbFloor = nbFloor;
        this.speed = speed;
    }

    public void setState(State state) {
        this.model.state = state;
    }

    /**
     * @return le modèle lié à cette ascenseur {@link ElevatorModel}
     */
    public ElevatorModel getModel() {
        return this.model;
    }

    /**
     * Démarre le simulateur.
     */
    public void start() {
        core = new Core();
        thread = new Thread(core);
        thread.start();
    }

    /**
     * Arrête le simulateur.
     * Si au bout d'un cours délai le simulateur n'a pas quitté, son thread est interrompu.
     *
     * @throws InterruptedException en cas d'échec de l'attente
     */
    public void stop() throws InterruptedException {
        if (thread != null && core != null && core.flag) {
            core.flag = false;
            wait(1000);
            if (thread.isAlive()) {
                thread.interrupt();
            }
            core = null;
            thread = null;
        }
    }

    private class Core implements Runnable {
        private boolean flag = true;
        private long startTime = 0;
        private boolean stopped = true;

        @Override
        public void run() {
            double msSpeed = speed *1000;

            long elapsed;
            while (flag) {
                final State state = model.state;
                elapsed = System.currentTimeMillis() - startTime;
                switch (state) {
                    case STOPPED:
                    case EMERGENCY:
                        if (!stopped) stopped = true;
                        break;

                    case MOVING_UP:
                    case MOVING_UP_STOP_NEXT:
                        if (stopped) start();
                        model.setPosition(model.position + elapsed/msSpeed);
                        break;

                    case MOVING_DOWN:
                    case MOVING_DOWN_STOP_NEXT:
                        if (stopped) start();
                        model.setPosition(model.position - elapsed/msSpeed);
                        break;
                }
            }
        }

        private void start() {
            stopped = false;
            startTime = System.currentTimeMillis();
        }
    }
}
