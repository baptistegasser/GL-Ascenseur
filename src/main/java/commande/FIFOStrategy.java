package commande;

import commande.request.Request;

import java.util.ArrayList;

public class FIFOStrategy implements SatisfactionStrategy{
    /**
     * Stratégie choisissant les requêtes les plus anciennes
     * @param listRequest liste des requêtes
     * @return la requête à exécuter, ou 0 s'il n'y en a plus
     */
    @Override
    public Request nextRequest(ArrayList<Request> listRequest) {
        if (listRequest.size() > 0) {
            return listRequest.get(0);
        } else {
            System.out.println("Aucunes requêtes. L'ascenceur reste à son étage actuel");
            return null;
        }
    }
}
