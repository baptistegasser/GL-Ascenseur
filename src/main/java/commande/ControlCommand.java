package commande;

import controller.Request;

import java.util.ArrayList;

public class ControlCommand {
    State state;

    Request currentRequest;
    ArrayList<Request> listRequest;

    public ControlCommand() {
        state = State.STOPPED;
        currentRequest = null;
        listRequest = new ArrayList<>();
    }

    public void addRequest(Request request) {
        if (currentRequest == null) currentRequest = request;
        else listRequest.add(request);
    }


}
