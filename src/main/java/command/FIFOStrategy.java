package command;

import command.request.Request;
import command.request.RequestType;

import java.util.ArrayList;

/**
 * Cette classe permet de choisir les retraites à traiter grâce à la méthode FIFO (First In First Out).
 * Cette méthode est très simple mais n'est pas du tout optimisée
 */
public class FIFOStrategy implements SatisfactionStrategy{
    /**
     * Stratégie choisissant les requêtes les plus anciennes
     * @param listRequest liste des requêtes
     * @return la requête à exécuter, ou 0 s'il n'y en a plus
     */
    @Override
    public Request nextRequest(ArrayList<Request> listRequest) {
        if (listRequest.size() > 0) {
            return listRequest.get(0);
        } else {
            System.out.println("Aucunes requêtes. L'ascenseur reste à son étage actuel");
            return null;
        }
    }

    @Override
    public ArrayList<Request> getListOfAction(ArrayList<Request> listRequest, Request currentRequest, RequestType requestType) {
        return new ArrayList<>();
    }
}
