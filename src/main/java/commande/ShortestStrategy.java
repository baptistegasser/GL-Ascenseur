package commande;

import controller.Request;
import controller.RequestType;
import ui.model.ElevatorModel;

import java.util.ArrayList;
import java.util.List;

public class ShortestStrategy  implements SatisfactionStrategy {
    public static Request chooseFloor(List<Request> fifo) {
        double currentPosition = ElevatorModel.position;
        Request removeRequest = null;
        if (fifo.isEmpty())                 return null;

        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.STOP_URGENCY) {
                fifo.remove(request);
                return null;
            }
        }
        int etageToGo = 0;
        double delta = 480;
        double tmpDelta;
        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.GO_TO) {
                if (request.getFloor() > currentPosition) tmpDelta = request.getFloor() - currentPosition;
                else tmpDelta = currentPosition - request.getFloor();
                if (tmpDelta < delta) {
                    delta = tmpDelta;
                    etageToGo = request.getFloor();
                    removeRequest = request;
                }
            }
        }
        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.OUTSIDE_UP || request.getRequestType() == RequestType.OUTSITE_DOWN) {
                if(currentPosition - etageToGo > 0) {

                }
            }
        }
        fifo.remove(removeRequest);
        return null;

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
