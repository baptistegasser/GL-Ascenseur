package command;

import commande.ShortestStrategy;
import commande.request.Request;
import commande.request.RequestType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShortestStrategyTest {


    @Test
    void nextRequestSimple() {
        ShortestStrategy strategy = new ShortestStrategy();
        ArrayList<Request> listRequest = new ArrayList<>();

        assertNull(strategy.nextRequest(listRequest));

        Request request = new Request(RequestType.GO_TO, 5);
        Request request2 = new Request(RequestType.GO_TO, 2);

        listRequest.add(request);
        listRequest.add(request2);
        System.out.println("L'ascenceur ira a l'étage " + strategy.nextRequest(listRequest).getPosition());

        assertEquals(strategy.nextRequest(listRequest), request);
    }

    @Test
    void nextRequest() {
        ShortestStrategy strategy = new ShortestStrategy();
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
        assertEquals(strategy.nextRequest(listRequest), request4);
    }
}