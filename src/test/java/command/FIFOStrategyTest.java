package command;

import command.FIFOStrategy;
import command.request.Request;
import command.request.RequestType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Cette classe permet de réaliser des test pour la classe FIFOStrategy
 */
public class FIFOStrategyTest {

    /**
     * Test simple qui permet de voir si c'est bien la première requête qui est exécutée
     */
    @Test
    public void nextRequestTest() {
        FIFOStrategy strategy = new FIFOStrategy();

        ArrayList<Request> listRequest = new ArrayList<>();

        assertNull(strategy.nextRequest(listRequest));

        Request request = new Request(RequestType.GO_TO, 5);
        listRequest.add(request);

        assertEquals(strategy.nextRequest(listRequest), request);
    }
}
