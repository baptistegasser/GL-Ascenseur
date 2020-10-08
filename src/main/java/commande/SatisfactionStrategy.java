package commande;

import commande.request.Request;

import java.util.ArrayList;

public interface SatisfactionStrategy {
    public Request nextRequest(ArrayList<Request> listRequest);
}
