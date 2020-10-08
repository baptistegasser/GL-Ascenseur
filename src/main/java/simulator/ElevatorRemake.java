package simulator;

import command.State;
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

    public ElevatorRemake(int nbFloor, int speed, ElevatorModel model) {
        this.model = model;
        this.model.state = State.STOPPED;
        this.model.nbFloor = nbFloor;
        this.speed = speed;
        this.start();
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
            Thread.sleep(500);
            if (thread.isAlive()) {
                thread.interrupt();
            }
            core = null;
            thread = null;
        }
    }

    private class Core implements Runnable {
        private boolean flag = true;
        private boolean stopped = true;
        private long last, elapsed;
        private int nextFloor = -1;
        private boolean isGoingToStop = false;

        @Override
        public void run() {
            double msSpeed = speed * 1000;
            last = System.currentTimeMillis();

            long time;
            while (flag) {
                time = System.currentTimeMillis();
                elapsed = time - last;
                last = time;

                switch (model.state) {
                    case STOPPED:
                    case EMERGENCY:
                        if (!stopped) stop();
                        break;

                    case MOVING_UP_STOP_NEXT:
                        if (!isGoingToStop) {
                            isGoingToStop = true;
                            double p = model.getPosition();
                            nextFloor = (int)(p - (p%1))+1;
                        }
                    case MOVING_UP:
                        up();
                        break;

                    case MOVING_DOWN_STOP_NEXT:
                        if (!isGoingToStop) {
                            isGoingToStop = true;
                            double p = model.getPosition();
                            nextFloor = (int)(p - (p%1));
                        }
                    case MOVING_DOWN:
                        down();
                        break;
                }
            }
        }

        private void stop() {
            stopped = true;
        }

        private void up() {
            if (stopped) {
                stopped = false;
                return;
            }

            double pos = model.getPosition() + elapsed/2000d;
            if (isGoingToStop && pos >= nextFloor) {
                isGoingToStop = false;
                model.setState(State.STOPPED);
                model.setPosition(nextFloor);
            } else if (pos > model.nbFloor) {
                model.setState(State.EMERGENCY);
                model.setPosition(model.nbFloor);
            } else {
                model.setPosition(pos);
            }
        }

        private void down() {
            if (stopped) {
                stopped = false;
                return;
            }

            double pos = model.getPosition() - elapsed/2000d;
            if (isGoingToStop && pos <= nextFloor) {
                isGoingToStop = false;
                model.setState(State.STOPPED);
                model.setPosition(nextFloor);
            } else if (pos < 0) {
                model.setState(State.EMERGENCY);
                model.setPosition(0);
            } else {
                model.setPosition(pos);
            }
        }
    }
}
