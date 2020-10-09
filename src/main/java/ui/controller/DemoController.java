package ui.controller;

import command.ControlCommand;
import command.request.Request;
import command.request.RequestType;
import command.model.Dir;
import ui.view.DemoView;

/**
 * Le contrôler chargé d'opérer la vue de démo.
 *
 * @see DemoView
 */
public class DemoController {
    /**
     * Le contrôleur commande utilisé durant l'utilisation de la démo.
     */
    private final ControlCommand controlCommand;

    public DemoController(ControlCommand controlCommand) {
        this.controlCommand = controlCommand;
    }

    /**
     * Fonction appelé lors ce que l'on souhaite arrêter l'ascenseur en urgence.
     */
    public void handleEmergencyStopRequest() {
        Request request = new Request(RequestType.URGENCY, -1);

        controlCommand.addRequest(request);
    }

    /**
     * Fonction appelé lorsque l'on souhaite allé à un étage depuis l'intérieur de l'ascenseur.
     *
     * @param floor l'étage de destination souhaité
     */
    public void handleFloorRequestInside(int floor) {
        Request request = new Request(RequestType.GO_TO, floor);

        controlCommand.addRequest(request);
    }

    /**
     * Fonction appelé lorsque l'on souhaite appelé l'ascenseur à notre étage.
     *
     * @param dir   la direction souhaité par l'utilisateur
     * @param floor l'étage d'où la requête émane
     */
    public void handleFloorRequestOutside(Dir dir, int floor) {
        Request request = null;
        if (dir == Dir.DOWN) request = new Request(RequestType.OUTSIDE_DOWN, floor);
        else if (dir == Dir.UP) request = new Request(RequestType.OUTSIDE_UP, floor);

        controlCommand.addRequest(request);
    }

    /**
     * Fonction appelé lorsqu'un technicien remet l'ascenseur en marche.
     */
    public void handleEmergencyStopExit() {
        Request request = new Request(RequestType.STOP_URGENCY, -1);

        controlCommand.addRequest(request);
    }
}
