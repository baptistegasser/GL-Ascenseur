package commande.request;

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

