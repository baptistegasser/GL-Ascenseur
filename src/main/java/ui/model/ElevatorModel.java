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
}
