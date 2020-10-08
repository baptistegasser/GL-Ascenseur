package command.request;

/**
 * Cette classe modélise une requête utilisateur
 * avec un type (requete depuis l'exterieur de l'ascenseur, requete intérieure, requete d'arret d'urgence)
 * ainsi que la position cible pour l'ascenseur (-1 si arret d'urgence)
 */
public class Request {
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
}

