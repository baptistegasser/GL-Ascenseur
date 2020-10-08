package commande;

import controller.Request;
import controller.RequestType;

import java.util.ArrayList;
import java.util.List;

public class FIFOStrategy implements SatisfactionStrategy{
    public static int execute (List<Request> fifo) {
        if (fifo.isEmpty()) return -2;
        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.STOP_URGENCY) {
                fifo.remove(request);
                return -1;
            }
        }
        int gotofloor = fifo.get(1).getFloor();
        fifo.remove(0);
        return gotofloor;
    }

    @Override
    public void execute() {

    }

    @Override
    public int nextRequest(ArrayList<Request> listRequest) {
        System.out.println("NEXT");
        System.out.println(listRequest);
        if (listRequest.size() > 0) {
            return 1;
        } else {
            System.out.println("Aucunes requêtes. L'ascenceur reste à son étage actuel");
            return 0;
        }
    }
}
