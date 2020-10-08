package commande;

import commande.request.Request;
import commande.request.RequestType;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet de choisir les retraites à traiter en fonction de leur distance. Cette stratégie est normalement plus rapide
 * que la stratégie FIFO.
 */
public class ShortestStrategy  implements SatisfactionStrategy {

    /**
     * Cette méthode permet de choisir la prochaine requête à traiter, et ainsi de s'y rendre
     * @param fifo La liste des requêtes à traiter
     * @return la requête à executer
     */
    public static Request chooseFloor(List<Request> fifo) {

        double etageToGo = 0;
        double delta = 480;
        double tmpDelta;
        double currentPosition = 4 ;
        Request removeRequest = null;

        // Si la liste de requete et vite, ou si elle contient une requete d'arret d'urgence on return null
        if (fifo.isEmpty())  return null;
        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.STOP_URGENCY) {
                //fifo.remove(request);
                return null;
            }
        }

        // On choisis tout d'abord l'étage le plus proche où se diriger
        // depuis l'intérieur de l'ascenseur

        for (Request request : fifo) {
            if (request.getRequestType() == RequestType.GO_TO) {
                if (request.getPosition() > currentPosition) tmpDelta = request.getPosition() - currentPosition;
                else tmpDelta = currentPosition - request.getPosition();
                if (tmpDelta < delta) {
                    delta = tmpDelta;
                    etageToGo = request.getPosition();
                    removeRequest = request;
                }
            }
        }

        // Ici on traite les requêtes qui on été faites depuis l'exterieur de l'ascenseur
        //TODO Ici c'est la première requete en OUTSIDE qui sera prise en compte, et non la plus proche. A fix ?
        for (Request request : fifo) {
            // si c'est une requete exterieur, on la traite
            if (request.getRequestType() == RequestType.OUTSIDE_UP || request.getRequestType() == RequestType.OUTSIDE_DOWN) {
                // si on descend
                if(currentPosition > etageToGo) {
                    // si l'étage de la demande est sur le "chemin"
                    if(request.getPosition() < currentPosition && request.getPosition() > etageToGo) {
                        // s'il souhaite descendre  (comme le trajet initiale), alors on s'y arrête)
                        if (request.getRequestType() == RequestType.OUTSIDE_DOWN) {
                            //etageToGo = request.getFloor();
                            removeRequest = request;
                            break;
                        }
                    }
                }
                // si on monte
                else if (etageToGo > currentPosition) {
                    // si l'étage de la demande est sur le "chemin"
                    if(request.getPosition() > currentPosition && request.getPosition() < etageToGo) {
                        // s'il souhaite monter (comme le trajet initiale), alors on s'y arrête)
                        if (request.getRequestType() == RequestType.OUTSIDE_UP) {
                            //etageToGo = request.getFloor();
                            removeRequest = request;
                            break;
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
        //System.out.println(listRequest);
        if (listRequest.size() > 0) {
            //  On retourne la requête à traiter
            return chooseFloor(listRequest);
        } else {
            System.out.println("Aucunes requêtes. L'ascenceur reste à son étage actuel");
            return null;
        }
    }
}
