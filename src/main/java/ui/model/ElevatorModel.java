package ui.model;

import utils.Observable;

/**
 * Le modèle représentant l'état de l'ascenseur à afficher par la vue de démonstration.
 * @see ui.view.DemoView
 */
public class ElevatorModel extends Observable<ElevatorModel> {
    public int nbFloor;
    public double position;
    public Object state;
    public static int currentFloor;

    public ElevatorModel(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
}
