package commande;

import controller.Request;
import controller.RequestType;

import java.util.ArrayList;
import java.util.List;

public class FIFOStrategy implements SatisfactionStrategy{
    @Override
    public Request nextRequest(ArrayList<Request> listRequest) {
        System.out.println("NEXT");
        System.out.println(listRequest);
        if (listRequest.size() > 0) {
            return listRequest.get(0);
        } else {
            System.out.println("Aucunes requêtes. L'ascenceur reste à son étage actuel");
            return null;
        }
    }
}
