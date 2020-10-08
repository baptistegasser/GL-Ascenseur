package ui.controller;

import ui.model.Dir;
import ui.view.DemoView;

/**
 * Le contrôler chargé d'opérer la vue de démo.
 * @see DemoView
 */
public class DemoController {
    /**
     * Fonction appelé lors ce que l'on souhaite arrêter l'ascenseur en urgence.
     */
    public void handleEmergencyStopRequest() {
        System.out.println("Emergency stop request");
    }

    /**
     * Fonction appelé lorsque l'on souhaite allé à un étage depuis l'intérieur de l'ascenseur.
     *
     * @param floor l'étage de destination souhaité
     */
    public void handleFloorRequestInside(int floor) {
        System.out.println("Request to go to floor " + floor);
    }

    /**
     * Fonction appelé lorsque l'on souhaite appelé l'ascenseur à notre étage.
     *
     * @param dir la direction souhaité par l'utilisateur
     * @param floor l'étage d'où la requête émane
     */
    public void handleFloorRequestOutside(Dir dir, int floor) {
        System.out.println("Request for the elevator to come at floor " + floor + " to go " + dir);
    }
}
