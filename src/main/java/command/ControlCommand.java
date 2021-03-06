package command;

import command.model.State;
import command.request.Request;
import command.request.RequestType;
import simulator.ElevatorSimulator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Le module de contrôle commande chargé de contrôler un ascenseur.
 */
public class ControlCommand {
    private State stateInEmergency;

    /**
     * La requête courante.
     */
    private Request currentRequest;
    /**
     * La liste des requêtes non traitées.
     */
    private ArrayList<Request> listRequest;
    /**
     * La simulation d'ascenseur contrôlé par ce contrôle commande.
     */
    private ElevatorSimulator simulator;
    /**
     * La strategies de satisfaction des requêtes.
     */
    private SatisfactionStrategy strategy;

    /**
     * Le thread où s'exécute la gestion des requêtes.
     */
    private Thread thread;
    /**
     * L'instance chargé de gérer les requêtes.
     */
    private ActionRequest action;

    public ControlCommand(ElevatorSimulator simulator, SatisfactionStrategy strategy) {
        stateInEmergency = State.STOPPED;
        currentRequest = null;
        listRequest = new ArrayList<>();
        this.simulator = simulator;
        this.strategy = strategy;
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
     * Pour une fermeture propre, attendre au 3s le temps que le thread soit en mode attente de requête
     * @throws InterruptedException en cas d'échec de l'attente
     */
    public void stop() throws InterruptedException {
        if (thread != null && action != null && action.flag) {
            action.flag = false;
            Thread.sleep(3000);
            if (thread.isAlive()) {
                thread.interrupt();
            }
            action = null;
            thread = null;
        }
    }

    /**
     * Ajoute une nouvelle requête à la liste de requête, si la liste est vide, place la requête en requête courante directement
     * @param request Requête à ajouter
     */
    public void addRequest(Request request) {
        System.out.println("Nouvelle requête : "+request);

        if (request == null) return;

        //Gestion des cas d'urgences
        if (request.getRequestType() == RequestType.URGENCY) {
            stateInEmergency = simulator.getModel().state;
            simulator.setState(State.EMERGENCY);
            return;
        }

        //Gestion da suppression du cas d'urgence
        if (request.getRequestType() == RequestType.STOP_URGENCY) {
            simulator.setState(stateInEmergency);
            stateInEmergency = State.STOPPED;
            return;
        }

        if (currentRequest == null) {
            currentRequest = request;
        }
        //Permet de ne pas avoir en 2 la request courante
        listRequest.add(request);

    }

    /***
     * Class permettant de gérer la liste de requêtes
     */
    @SuppressWarnings("BusyWait")
    public class ActionRequest implements Runnable {
        private boolean flag = true;
        private ArrayList<Request> stopRequests;

        @Override
        public void run() {
            System.out.println("Démarrage du Thread Action");

            while (flag) {
                try {
                    //System.out.println(currentRequest);
                    if (currentRequest != null) {
                        System.out.println("Requête courante : "+ currentRequest);

                        if (simulator.getModel().getPosition() != currentRequest.getPosition()) {
                            if (simulator.getModel().getPosition() < currentRequest.getPosition()) {
                                goToUp();
                            } else{
                                goToDown();
                            }
                            System.out.println("ARRIVÉ AU "+simulator.getModel().getPosition());
                            stopRequests = new ArrayList<>();
                            Thread.sleep(3000);
                        }
                        listRequest.remove(currentRequest);
                        currentRequest = strategy.nextRequest(listRequest);
                    }

                    Thread.sleep(250);

                }catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
            System.out.println("Fin Thread");
        }

        /**
         * Ordonne tous les arrêt à effectuer pendant une monté
         * @throws InterruptedException en cas d'interruption pendant le traitement
         */
        public void goToUp() throws InterruptedException {
            stopRequests = strategy.getListOfAction(listRequest, currentRequest, RequestType.OUTSIDE_UP);
            Collections.sort(stopRequests);
            System.out.println("Liste requête Up: "+stopRequests);
            Request currentRequestMaster = currentRequest;

            for (int i = 0; i<stopRequests.size();i++) {
                currentRequest = stopRequests.get(i);
                moveToUp();
                listRequest.remove(currentRequest);
                System.out.println("ARRIVÉ AU "+simulator.getModel().getPosition());
                Thread.sleep(3000);
            }

            currentRequest = currentRequestMaster;
            moveToUp();
        }

        /**
         * Ordonne la simulation de monter la cabine vers l'étage de la requête courante
         * @throws InterruptedException en cas d'interruption pendant le traitement
         */
        public void moveToUp() throws InterruptedException {
            System.out.println("EN MONTÉ");

            simulator.setState(State.MOVING_UP);
            //Attend que la cabine arrive pour l'arrêt aux prochain
            while (simulator.getModel().getPosition()<currentRequest.getPosition()-1) {
                Thread.sleep(250);
            }
            simulator.setState(State.MOVING_UP_STOP_NEXT);

            //Attend que l'ascenseur arrive a l'étage
            while (simulator.getModel().getPosition() < currentRequest.getPosition()) {
                Thread.sleep(250);
            }
        }

        /**
         * Ordonne tous les arrêt à effectuer pendant une descente
         * @throws InterruptedException en cas d'interruption pendant le traitement
         */
        public void goToDown() throws InterruptedException {
            stopRequests = strategy.getListOfAction(listRequest, currentRequest, RequestType.OUTSIDE_DOWN);
            Collections.sort(stopRequests, Request.RequestComparator);
            System.out.println("Liste requête Down: "+stopRequests);
            Request currentRequestMaster = currentRequest;

            for (int i = 0; i<stopRequests.size();i++) {
                currentRequest = stopRequests.get(i);
                moveToDown();
                listRequest.remove(currentRequest);
                System.out.println("ARRIVÉ AU "+simulator.getModel().getPosition());
                Thread.sleep(3000);
            }

            currentRequest = currentRequestMaster;
            moveToDown();
        }

        /**
         * Ordonne la simulation de descendre la cabine vers l'étage de la requête courante
         * @throws InterruptedException en cas d'interruption pendant le traitement
         */
        public void moveToDown() throws InterruptedException {
            System.out.println("EN DESCENTE");
            simulator.setState(State.MOVING_DOWN);

            while (simulator.getModel().getPosition()>currentRequest.getPosition()+1) {
                Thread.sleep(250);
            }
            simulator.setState(State.MOVING_DOWN_STOP_NEXT);

            while (simulator.getModel().getPosition() > currentRequest.getPosition()) {
                Thread.sleep(250);
            }
        }

        public ArrayList<Request> getStopRequests() {
            return stopRequests;
        }
    } // Action

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public ArrayList<Request> getListRequest() {
        return listRequest;
    }

    public void setCurrentRequest(Request currentRequest) {
        this.currentRequest = currentRequest;
    }

    public State getStateInEmergency() {
        return stateInEmergency;
    }

    public ActionRequest getAction() {
        return action;
    }
}
