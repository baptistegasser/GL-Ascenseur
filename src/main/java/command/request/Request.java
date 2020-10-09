package command.request;

import java.util.Comparator;

/**
 * Cette classe modélise une requête utilisateur
 * avec un type (requete depuis l'exterieur de l'ascenseur, requete intérieure, requete d'arret d'urgence)
 * ainsi que la position cible pour l'ascenseur (-1 si arret d'urgence)
 */
public class Request implements Comparable<Request> {
    RequestType requestType;
    double position; // -1 si arret d'urgence, sinon floor de destination

    public Request(RequestType requestType, double floor) {
        this.requestType = requestType;
        this.position = floor;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public double getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestType=" + requestType +
                ", floor=" + position +
                '}';
    }

    @Override
    public int compareTo(Request o) {
        return (int) (this.getPosition() - o.getPosition());
    }

    public static Comparator<Request> RequestComparator = new Comparator<Request>() {

        public int compare(Request request1, Request request2) {
            return (int) (request2.getPosition() - request1.getPosition());
        }

    };
}

