package command;

import command.request.Request;
import command.request.RequestType;

import java.util.ArrayList;

/**
 * Cette interface permet d'ajouter des stratégies de déplacement de l'ascenseur à volonté grâce à nextRequest()
 */
public interface SatisfactionStrategy {
    /**
     * Renvoie La requête à executer
     * @param listRequest La liste des requêtes en attente
     * @return La requête à executer
     */
    Request nextRequest(ArrayList<Request> listRequest);

    /**
     * Renvoie la liste des actions à effectuer
     * @param listRequest
     * @param currentRequest
     * @param requestType
     * @return La liste des actions
     */
    ArrayList<Request> getListOfAction(ArrayList<Request> listRequest, Request currentRequest, RequestType requestType);
}
