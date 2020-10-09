package simulator;

import command.model.State;
import command.model.ElevatorModel;

/**
 * Un simulateur d'ascenseur utilisable à des fin de démonstration.
 */
public class ElevatorSimulator {
    /**
     * Vitesse de l'ascenseur en seconde/étage.
     */
    private final int speed;
    /**
     * Le modèle contenant les données de ce simulateur.
     */
    private final ElevatorModel model;
    /**
     * Le thread exécutant le traitement de la simulation.
     */
    private Thread thread;
    /**
     * Le coeur du simulateur.
     *
     * @see SimulatorRunnable pour le fonctionnement.
     */
    private SimulatorRunnable runnable;

    public ElevatorSimulator(int nbFloor, int speed) {
        this.model = new ElevatorModel();
        this.model.state = State.STOPPED;
        this.model.nbFloor = nbFloor;
        this.speed = speed;
    }

    /**
     * Change l'état de l'ascenseur simulé.
     *
     * @param state le nouvel état
     */
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
        runnable = new SimulatorRunnable(speed, getModel());
        thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Arrête le simulateur.
     * Si au bout d'un cours délai le simulateur n'a pas quitté, son thread est interrompu.
     *
     * @throws InterruptedException en cas d'échec de l'attente
     */
    public void stop() throws InterruptedException {
        if (runnable != null && runnable.isRunning()) {
            runnable.stop();
            Thread.sleep(500);
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }
    }
}
