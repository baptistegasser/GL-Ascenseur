package commande.request;

public class Request {
    RequestType requestType;
    int floor; // null si arret d'urgence, sinon floor de destination

    public Request(RequestType requestType, int floor) {
        this.requestType = requestType;
        this.floor = floor;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestType=" + requestType +
                ", floor=" + floor +
                '}';
    }
}

