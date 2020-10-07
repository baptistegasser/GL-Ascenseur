package commande;

import controller.Request;
import controller.RequestType;

import java.util.List;

public class ShortestStrategy {
    public static int execute (List<Request> fifo, double currentPosition) {
        if (fifo.isEmpty()) return -2;
        for (Request request: fifo) {
            if (request.getRequestType() == RequestType.STOP_URGENCY)  return -1;
        }
        int etageToGo= 0;
        double delta = 480;
        double tmpDelta;
        for (Request request: fifo) {
            if(request.getRequestType() == RequestType.GO_TO) {
                if(request.getFloor() > currentPosition)  tmpDelta = request.getFloor() - currentPosition;
                else tmpDelta = currentPosition - request.getFloor();
                if (tmpDelta > delta) delta = tmpDelta;
            }
        }
        return etageToGo;
    }
}
