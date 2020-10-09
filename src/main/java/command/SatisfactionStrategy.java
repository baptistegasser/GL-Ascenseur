package command;

import command.request.Request;
import command.request.RequestType;

import java.util.ArrayList;

/**
 * Cette interface permet d'ajouter des stratégies de déplacement de l'ascenseur à volonté grâce à nextRequest()
 */
public interface SatisfactionStrategy {
    public Request nextRequest(ArrayList<Request> listRequest);
    public ArrayList<Request> getListOfAction(ArrayList<Request> listRequest, Request currentRequest, RequestType requestType);
}
