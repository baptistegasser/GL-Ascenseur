package commande;

import controller.Request;
import controller.RequestType;

import java.util.List;

public class FIFOStrategy {
    public static int execute (List<Request> fifo) {
        if (fifo.isEmpty()) return -2;
        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.STOP_URGENCY) {
                fifo.remove(request);
                return -1;
            }
        }
        int gotofloor = fifo.get(0).getFloor();
        fifo.remove(0);
        return gotofloor;
    }
}
