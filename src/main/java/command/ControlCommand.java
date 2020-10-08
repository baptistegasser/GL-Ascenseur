package command;

import command.request.Request;
import command.request.RequestType;
import simulator.ElevatorRemake;
import simulator.ElevatorSimulator;
import ui.model.Dir;
import ui.model.ElevatorModel;

import java.awt.image.renderable.RenderableImageProducer;
import java.util.ArrayList;

public class ControlCommand {
    State state;

    Request currentRequest;
    ArrayList<Request> listRequest;

    ElevatorRemake simulator;

    ElevatorModel model;

    SatisfactionStrategy strategy;

    Thread thread;
    ActionRequest action;

    public ControlCommand(ElevatorRemake simulator, SatisfactionStrategy strategy, ElevatorModel model) {
        state = State.STOPPED;
        currentRequest = null;
        listRequest = new ArrayList<>();
        this.simulator = simulator;
        this.strategy = strategy;
        this.model = model;
        start();
    }

    /**
     * Ajoute une nouvelle requête à la liste de requête, si la liste est vide, place la requête en requête courante directement
     * @param request Requête à ajouter
     */
    public void addRequest(Request request) {
        System.out.println(request);

        if (request == null) return;

        if (request.getRequestType() == RequestType.URGENCY) {
            simulator.setState(State.EMERGENCY);
        }

        if (request.getRequestType() == RequestType.STOP_URGENCY) {
            simulator.setState(State.STOPPED);
        }

        if (currentRequest == null) {
            currentRequest = request;
        }
        //Permet de ne pas avoir en 2 la request courante
        listRequest.add(request);

    }

    /**
     * Démarre le gestionnaire de requêtes.
     */
    public void start() {
        action = new ActionRequest();
        thread = new Thread(action);
        thread.start();
    }

    /**
     * Arrête le gestionnaire de requêtes.
     * Si au bout d'un cours délai le simulateur n'a pas quitté, son thread est interrompu.
     *
     * @throws InterruptedException en cas d'échec de l'attente
     */
    public void stop() throws InterruptedException {
        if (thread != null && action != null && action.flag) {
            action.flag = false;
            Thread.sleep(500);
            if (thread.isAlive()) {
                thread.interrupt();
            }
            action = null;
            thread = null;
        }
    }

    public class ActionRequest implements Runnable {
        private boolean flag = true;

        @Override
        public void run() {
            System.out.println("Démarrage du Thread Action");

            while (flag) {
                try {
                    //System.out.println(currentRequest);
                    if (currentRequest != null) {
                        System.out.println(currentRequest);

                        //Cas d'une requête Go_To
                        if (currentRequest.getRequestType() == RequestType.GO_TO) {
                            System.out.println("GOTO");
                            if (model.getPosition() < currentRequest.getPosition()) {
                                goToUp();
                            } else if (model.getPosition() > currentRequest.getPosition()) {
                                goToDown();
                            } else return;
                        }
                     /*if (requests.size() > 0) {
                    //System.out.println("Requete : " + requests);

                    for (int i = 0; i < requests.size(); ) {
                        if (model.getPosition() == requests.get(i).getPosition()) {
                            //System.out.println("While : "+requests.get(0));
                            simulator.setState(stateRequestsForStop);
                            i++;
                            }
                        }
                    }*/

                        Thread.sleep(3000);

                        listRequest.remove(currentRequest);
                        currentRequest = strategy.nextRequest(listRequest);
                    }

                    Thread.sleep(250);

                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Fonction prototype pour organiser un voyage (A revoir)
         * @param requestType
         * @return
         */
        public ArrayList<Request> getListOfAction(RequestType requestType) {
            ArrayList<Request> returnList = new ArrayList<>();

            for (Request request : listRequest) {
                if (request.getRequestType() == requestType) {
                    if (requestType == RequestType.OUTSIDE_UP) {
                        if (request.getPosition()>model.getPosition() && request.getPosition()<currentRequest.getPosition()){
                            System.out.println(request);
                            returnList.add(request);
                        }
                    }
                }
            }
            System.out.println(returnList);

            return returnList;
        }

        public void goToUp() throws InterruptedException {
            System.out.println("Up");
            //stateRequestsForStop = State.MOVING_UP_STOP_NEXT;
            //requests = getListOfAction(RequestType.OUTSIDE_UP);

            simulator.setState(State.MOVING_UP);
            //Attend que la cabine arrive pour l'arrêt aux prochain
            while (simulator.getModel().getPosition()<currentRequest.getPosition()-1) {
                //System.out.println("Position : "+ simulator.getModel().getPosition());
                Thread.sleep(250);
            }
            simulator.setState(State.MOVING_UP_STOP_NEXT);

            //Attend que l'ascenseur arrive a l'étage
            while (simulator.getModel().getPosition() == currentRequest.getPosition()) {
                Thread.sleep(250);
            }
        }

        public void goToDown() throws InterruptedException {
            System.out.println("Down");
            //stateRequestsForStop = State.MOVING_DOWN_STOP_NEXT;
            //requests = getListOfAction(RequestType.OUTSIDE_DOWN);
            simulator.setState(State.MOVING_DOWN);

            while (simulator.getModel().getPosition()>currentRequest.getPosition()+1) {
                //System.out.println("Position : "+ simulator.getModel().getPosition());
                Thread.sleep(250);
            }
            simulator.setState(State.MOVING_DOWN_STOP_NEXT);

            while (simulator.getModel().getPosition() == currentRequest.getPosition()) {
                Thread.sleep(250);
            }
        }

    }

    /**
     * Execute les requêtes en fonction de la stratégie
     */

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public ArrayList<Request> getListRequest() {
        return listRequest;
    }

    public void setCurrentRequest(Request currentRequest) {
        this.currentRequest = currentRequest;
    }
}
