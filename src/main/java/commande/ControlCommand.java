package commande;

import controller.Request;
import simulator.ElevatorSimulator;

import java.util.ArrayList;

public class ControlCommand {
    State state;

    Request currentRequest;
    ArrayList<Request> listRequest;

    ElevatorSimulator simulator;

    public ControlCommand(ElevatorSimulator simulator) {
        state = State.STOPPED;
        currentRequest = null;
        listRequest = new ArrayList<>();
        this.simulator = simulator;
    }

    public void addRequest(Request request) {
        if (currentRequest == null) currentRequest = request;
        else listRequest.add(request);
    }
}
