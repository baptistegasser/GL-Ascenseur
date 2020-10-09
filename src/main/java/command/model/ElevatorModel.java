package command.model;

import utils.Observable;

/**
 * Le modèle représentant l'état de l'ascenseur à afficher par la vue de démonstration.
 *
 * @see ui.view.DemoView
 */
public class ElevatorModel extends Observable<ElevatorModel> {
    /**
     * Le nombre d'étage de l'ascenseur.
     * Exemple si nbFloor = 9 alors on a le RDC et 9 étages
     */
    public int nbFloor;
    /**
     * L'état actuel de l'ascenseur.
     */
    public State state;
    /**
     * La position actuel de l'ascenseur.
     */
    public double position;

    public ElevatorModel() {
        position = 0;
        state = State.STOPPED;
    }

    /**
     * @return l'état de l'ascenseur
     */
    public State getState() {
        return state;
    }

    /**
     * Modifie l'état de l'ascenseur et notifie les observers.
     *
     * @param state le nouvelle état
     */
    public void setState(State state) {
        this.state = state;
        notifyObservers();
    }

    /**
     * @return la position de l'ascenseur
     */
    public double getPosition() {
        return position;
    }

    /**
     * Modifie la position de l'ascenseur et notifie les observers.
     *
     * @param position la nouvelle position
     */
    public void setPosition(double position) {
        this.position = position;
        notifyObservers();
    }
}
