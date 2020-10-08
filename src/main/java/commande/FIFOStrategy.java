package commande;

import commande.request.Request;

import java.util.ArrayList;

/**
 * Cette classe permet de choisir les retraites à traiter grâce à la méthode FIFO (First In First Out).
 * Cette méthode est très simple mais n'est pas du tout optimisée
 */
public class FIFOStrategy implements SatisfactionStrategy{
    /**
     * Cette méthode renvoie la première requête de la liste des requêtes
     * Si la liste est vide, elle retourne null
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
