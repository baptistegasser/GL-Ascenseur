package commande;

import commande.request.Request;
import commande.request.RequestType;
import simulator.ElevatorSimulator;

import java.util.ArrayList;

public class ControlCommand {
    State state;

    Request currentRequest;
    ArrayList<Request> listRequest;

    ElevatorSimulator simulator;

    SatisfactionStrategy strategy;

    public ControlCommand(ElevatorSimulator simulator, SatisfactionStrategy strategy) {
        state = State.STOPPED;
        currentRequest = null;
        listRequest = new ArrayList<>();
        this.simulator = simulator;
        this.strategy = strategy;
    }

    public void addRequest(Request request) {
        System.out.println(request);

        if (request.getRequestType() == RequestType.STOP_URGENCY) {
            System.out.println("Arrêt d'urgence du simulateur");
        }

        if (currentRequest == null) {
            currentRequest = request;
            //Permet de ne pas avoir en 2 la request courante
            listRequest.add(request);
            action();
        }
        else listRequest.add(request);
    }

    /**
     * Execute les requêtes en fonction de la stratégie
     */
    public void action() {
        new Thread(new Runnable() {
            public void run() {
               if (currentRequest.getRequestType() == RequestType.GO_TO) {
                    simulator.goTo(currentRequest.getPosition());
                }
               // A remplacer par strategy.execute(listRequest)
                /*int result = FIFOStrategy.execute(listRequest);
                if (result == -2) {
                    System.out.println("Aucunes requêtes. L'ascenceur reste à son étage actuel");
                }
                else simulator.goTo(result);*/

                //Passage au suivant
                listRequest.remove(currentRequest);
                currentRequest = strategy.nextRequest(listRequest);
                if (currentRequest != null) action();
                System.out.println(currentRequest);
            }
        }).start();

    }
}
