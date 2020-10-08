package ui.model;

import commande.State;
import utils.Observable;

/**
 * Le modèle représentant l'état de l'ascenseur à afficher par la vue de démonstration.
 * @see ui.view.DemoView
 */
public class ElevatorModel extends Observable<ElevatorModel> {
    public int nbFloor;
    public double position;
    public State state;

    public ElevatorModel() {
        position = 0;
    }

    public void setNbFloor(int nbFloor) {
        this.nbFloor = nbFloor;
        notifyObservers();
    }

    public void setPosition(double position) {
        this.position = position;
        notifyObservers();
    }

    public void setState(State state) {
        this.state = state;
        notifyObservers();
    }
}
