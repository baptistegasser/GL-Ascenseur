package command;

import command.ShortestStrategy;
import command.model.ElevatorModel;
import command.request.Request;
import command.request.RequestType;
import org.junit.jupiter.api.Test;
import simulator.ElevatorSimulator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Cette classe permet de réaliser des test pour la classe ShortestStrategy
 */
class ShortestStrategyTest {


    /**
     * Test dans le cas ou l'ascenseur, situé au 4eme, doit choisir entre l'étage 5 ou 2 en destination.
     */
    @Test
    void nextRequestSimple() {
        ShortestStrategy strategy = new ShortestStrategy(new ElevatorModel());
        ArrayList<Request> listRequest = new ArrayList<>();

        assertNull(strategy.nextRequest(listRequest));

        Request request = new Request(RequestType.GO_TO, 5);
        Request request2 = new Request(RequestType.GO_TO, 2);

        listRequest.add(request);
        listRequest.add(request2);
        System.out.println("L'ascenceur ira a l'étage " + strategy.nextRequest(listRequest).getPosition());

        assertEquals(strategy.nextRequest(listRequest), request2);
    }

    /**
     * Test plus poussé dans le cas ou l'ascenseur doit prendre quelqu'un sur son chemin
     */
    @Test
    void nextRequest() {
        ElevatorSimulator ElevatorSimulator = new ElevatorSimulator(6, 3);
        ShortestStrategy strategy = new ShortestStrategy(ElevatorSimulator.getModel());
        ArrayList<Request> listRequest = new ArrayList<>();

        assertNull(strategy.nextRequest(listRequest));

        Request request = new Request(RequestType.GO_TO, 7);
        Request request2 = new Request(RequestType.GO_TO, 1);
        Request request3 = new Request(RequestType.OUTSIDE_UP, 5);
        Request request4 = new Request(RequestType.OUTSIDE_UP, 6);
        Request request5 = new Request(RequestType.OUTSIDE_DOWN, 5);
        Request request6 = new Request(RequestType.OUTSIDE_UP, 3);

        listRequest.add(request);
        listRequest.add(request2);
        listRequest.add(request3);
        listRequest.add(request4);
        listRequest.add(request5);
        listRequest.add(request6);


        System.out.println("L'ascenceur ira a l'étage " + strategy.nextRequest(listRequest).getPosition());
        assertEquals(strategy.nextRequest(listRequest), request2);
    }
}