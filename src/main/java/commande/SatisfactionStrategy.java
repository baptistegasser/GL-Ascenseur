package commande;

import controller.Request;

import java.util.ArrayList;

public interface SatisfactionStrategy {
    public void execute();
    public int nextRequest(ArrayList<Request> listRequest);
}
