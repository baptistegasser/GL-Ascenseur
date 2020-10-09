package command;

import command.request.Request;
import command.request.RequestType;
import ui.model.ElevatorModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet de choisir les retraites à traiter en fonction de leur distance. Cette stratégie est normalement plus rapide
 * que la stratégie FIFO.
 */
public class ShortestStrategy  implements SatisfactionStrategy {
    ElevatorModel model;
    public ShortestStrategy(ElevatorModel model) {
        this.model = model;
    }

    //A modifier TODO
    /**
     * Cette méthode permet de choisir la prochaine requête à traiter, et ainsi de s'y rendre
     * @param fifo La liste des requêtes à traiter
     * @return la requête à executer
     */
    public  Request chooseFloor(List<Request> fifo) {

        double etageToGo = 0;
        double delta = 480;
        double tmpDelta;
        double currentPosition = this.model.position ;
        Request removeRequest = null;

        // Si la liste de requete et vite, ou si elle contient une requete d'arret d'urgence on return null
        if (fifo.isEmpty())  return null;
        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.URGENCY) {
                //fifo.remove(request);
                return null;
            }
        }

        // On choisis tout d'abord l'étage le plus proche où se diriger
        // depuis l'intérieur de l'ascenseur

        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.GO_TO) {
                if (request.getPosition() > currentPosition) tmpDelta = request.getPosition() - currentPosition;
                else tmpDelta = currentPosition - request.getPosition();
                if (tmpDelta < delta) {
                    delta = tmpDelta;
                    etageToGo = request.getPosition();
                    removeRequest = request;
                }
            }
        }

        // Ici on traite les requêtes qui on été faites depuis l'exterieur de l'ascenseur
        //TODO Ici c'est la première requete en OUTSIDE qui sera prise en compte, et non la plus proche. A fix ?
        for (Request request : fifo) {
            // si c'est une requete exterieur, on la traite
            if (request.getRequestType() == RequestType.OUTSIDE_UP || request.getRequestType() == RequestType.OUTSIDE_DOWN) {
                // si on descend
                if(currentPosition > etageToGo) {
                    // si l'étage de la demande est sur le "chemin"
                    if(request.getPosition() < currentPosition && request.getPosition() > etageToGo) {
                        // s'il souhaite descendre  (comme le trajet initiale), alors on s'y arrête)
                        if (request.getRequestType() == RequestType.OUTSIDE_DOWN) {
                            //etageToGo = request.getFloor();
                            removeRequest = request;
                            break;
                        }
                    }
                }
                // si on monte
                else if (etageToGo > currentPosition) {
                    // si l'étage de la demande est sur le "chemin"
                    if(request.getPosition() > currentPosition && request.getPosition() < etageToGo) {
                        // s'il souhaite monter (comme le trajet initiale), alors on s'y arrête)
                        if (request.getRequestType() == RequestType.OUTSIDE_UP) {
                            //etageToGo = request.getFloor();
                            removeRequest = request;
                            break;
                        }
                    }
                }
            }
        }
        return removeRequest;
    }

    /**
     * Cette méthode renvoie la requête renvoyée par chooseFloor()
     * Si la liste est vide, elle retourne null
     */
    @Override
    public Request nextRequest(ArrayList<Request> listRequest) {
        System.out.println("NEXT");
        //System.out.println(listRequest);
        /*if (listRequest.size() > 0) {
            //  On retourne la requête à traiter
            return chooseFloor(listRequest);
        } else {
            System.out.println("Aucunes requêtes. L'ascenceur reste à son étage actuel");
            return null;
        }*/

        if (listRequest.size() > 0) {
            int minFloor = 100;
            Request requestFinal = null;
            boolean haveGoto = false;

            for (Request request : listRequest) {
                if (request.getRequestType() == RequestType.GO_TO) {
                    if (haveGoto) {
                        int floorDiff = (int) Math.abs(request.getPosition() - model.getPosition());
                        if (floorDiff < minFloor) {
                            requestFinal = request;
                            minFloor = floorDiff;
                        }
                    } else {
                        haveGoto = true;
                        requestFinal = request;
                        minFloor = (int) Math.abs(request.getPosition() - model.getPosition());
                    }
                } else {
                    if (!haveGoto) {
                        int floorDiff = (int) Math.abs(request.getPosition() - model.getPosition());
                        if (floorDiff < minFloor) {
                            requestFinal = request;
                            minFloor = floorDiff;
                        }
                    }
                }
            }
            return requestFinal;
        } else {
            System.out.println("Aucunes requêtes. L'ascenceur reste à son étage actuel");
            return null;
        }
    }

    /**
     * Retourne toutes les requêtes à exécuter pendant un voyage
     * @param requestType liste de requête à exécuter pendant le voyage
     * @return
     */
    @Override
    public ArrayList<Request> getListOfAction(ArrayList<Request> listRequest, Request currentRequest, RequestType requestType) {
        ArrayList<Request> returnList = new ArrayList<>();

        for (Request request : listRequest) {
            if (request.getRequestType() == requestType || request.getRequestType() == RequestType.GO_TO) {
                if (requestType == RequestType.OUTSIDE_UP) {
                    if (request.getPosition()>model.getPosition() && request.getPosition()<currentRequest.getPosition()){
                        returnList.add(request);
                    }
                } else if (requestType == RequestType.OUTSIDE_DOWN) {
                    if (request.getPosition()<model.getPosition() && request.getPosition()>currentRequest.getPosition()){
                        returnList.add(request);
                    }
                }
            }
        }

        return returnList;
    }
}
