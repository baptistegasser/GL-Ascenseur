package commande;

import controller.Request;
import controller.RequestType;
import simulator.ElevatorSimulator;

import java.util.ArrayList;

public class ControlCommand {
    State state;

    Request currentRequest;
    ArrayList<Request> listRequest;

    ElevatorSimulator simulator;

    public ControlCommand(ElevatorSimulator simulator) {
        state = State.STOPPED;
        currentRequest = null;
        listRequest = new ArrayList<>();
        this.simulator = simulator;
    }

    public void addRequest(Request request) {
        if (currentRequest == null) {
            currentRequest = request;
            //Permet de ne pas avoir en 2 la request courante
            listRequest.add(request);
            action();
        }
        else listRequest.add(request);
    }

    /**
     * Execute les requetes
     */
    public void action() {
        new Thread(new Runnable() {
            public void run() {
               /* if (currentRequest.getRequestType() == RequestType.GO_TO) {
                    simulator.goTo(currentRequest.getFloor());
                }*/
               // A remplacer par strategy.execute(listRequest)
                int result = ShortestStrategy.execute(listRequest, simulator.getPosition());
                if (result == -1) {
                    System.out.println("Arrêt d'urgence du simulateur");
                    // simulator.stop();
                }
                else if (result == -2) {
                    System.out.println("Aucunes requêtes. L'ascenceur reste à son étage actuel");
                }
                else simulator.goTo(result);
            }
        }).start();

        //passage à la requete suivante
        listRequest.remove(currentRequest);
        if (listRequest.size() > 0) {
            currentRequest = listRequest.get(0);
            action();
        } else currentRequest = null;
    }
}
