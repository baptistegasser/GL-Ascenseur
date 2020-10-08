package commande;

import commande.request.Request;
import commande.request.RequestType;
import ui.model.ElevatorModel;

import java.util.ArrayList;
import java.util.List;

public class ShortestStrategy  implements SatisfactionStrategy {

    public static Request chooseFloor(List<Request> fifo) {

        int etageToGo = 0;
        double delta = 480;
        double tmpDelta;
        double currentPosition = ElevatorModel.position;
        Request removeRequest = null;

        if (fifo.isEmpty())  return null;
        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.STOP_URGENCY) {
                //fifo.remove(request);
                return null;
            }
        }

        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.GO_TO) {
                if (request.getFloor() > currentPosition) tmpDelta = request.getFloor() - currentPosition;
                else tmpDelta = currentPosition - request.getFloor();
                if (tmpDelta < delta) {
                    delta = tmpDelta;
                    //etageToGo = request.getFloor();
                    removeRequest = request;
                }
            }
        }

        //TODO Ici c'est la dernière requete en OUTSIDE qui sera prise en compte, et non la plus proche. A fix ?
        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.OUTSIDE_UP || request.getRequestType() == RequestType.OUTSITE_DOWN) {
                if(currentPosition > etageToGo) { // descente
                    if(request.getFloor() < currentPosition && request.getFloor() > etageToGo) {
                        if (request.getRequestType() == RequestType.OUTSITE_DOWN) {
                            //etageToGo = request.getFloor();
                            removeRequest = request;
                        }
                    }
                }
                else if (etageToGo > currentPosition) { // monté
                    if(request.getFloor() > currentPosition && request.getFloor() < etageToGo) {
                        if (request.getRequestType() == RequestType.OUTSIDE_UP) {
                            //etageToGo = request.getFloor();
                            removeRequest = request;
                        }
                    }
                }
            }
        }
        return removeRequest;
    }


    @Override
    public Request nextRequest(ArrayList<Request> listRequest) {
        System.out.println("NEXT");
        System.out.println(listRequest);
        if (listRequest.size() > 0) {
            return chooseFloor(listRequest);
        } else {
            System.out.println("Aucunes requêtes. L'ascenceur reste à son étage actuel");
            return null;
        }
    }
}
