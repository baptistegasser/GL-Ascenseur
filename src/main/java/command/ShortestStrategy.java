package command;

import command.request.Request;
import command.request.RequestType;
import command.model.ElevatorModel;

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

    /**
     * Cette méthode permet de choisir la prochaine requête à traiter, elle choisie en priorité les requêtes Goto les plus proche
     * @param listRequest La liste des requêtes à traiter
     * @return la requête à exécuter
     */
    public Request chooseFloor(List<Request> listRequest) {

        int minFloor = 100;//On prend une valeur assez élevé
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
    }

    /**
     * Cette méthode renvoie la requête renvoyée par chooseFloor()
     * Si la liste est vide, elle retourne null
     * @return la requête à exécuter
     */
    @Override
    public Request nextRequest(ArrayList<Request> listRequest) {
        if (listRequest.size() > 0) {
            return chooseFloor(listRequest);
        } else {
            System.out.println("Aucunes requêtes. L'ascenseur reste à son étage actuel");
            return null;
        }
    }

    /**
     * Retourne toutes les requêtes à exécuter pendant un voyage
     * @param requestType liste de requête à exécuter pendant le voyage
     * @return la liste de requêtes
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
