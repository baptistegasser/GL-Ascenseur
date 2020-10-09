package command;

import command.request.Request;
import command.request.RequestType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulator.ElevatorSimulator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Different test pour le controlCommand utilisant la strategy Shortest
 */
public class ControlCommandTest {

    ElevatorSimulator simulator;
    ControlCommand controlCommand;

    @BeforeEach
    void setUp() {
        simulator = new ElevatorSimulator(6,3);
        controlCommand = new ControlCommand(simulator, new ShortestStrategy(simulator.getModel()));
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        controlCommand.stop();
        simulator.stop();
    }

    /**
     * Vérification que la méthode stop fonctionne.
     */
    @Test
    void stop() throws InterruptedException {
        controlCommand.start();
        Thread.sleep(500);
        assertDoesNotThrow(() -> controlCommand.stop());
    }

    /**
     * Verification de ma méthode addRequest dans le cas des requêtes liées aux états d'urgence
     */
    @Test
    public void addRequestEmergencyTest() {
        Request request = new Request(RequestType.URGENCY, -1);
        Request request1 = new Request(RequestType.STOP_URGENCY, -1);

        controlCommand.addRequest(request);
        assertEquals(simulator.getModel().getState(), State.EMERGENCY);
        assertEquals(controlCommand.getStateInEmergency(), State.STOPPED);

        controlCommand.addRequest(request1);
        assertEquals(simulator.getModel().getState(), State.STOPPED);
        assertEquals(controlCommand.getStateInEmergency(), State.STOPPED);

        simulator.start();
        simulator.setState(State.MOVING_UP);

        controlCommand.addRequest(request);
        assertEquals(simulator.getModel().getState(), State.EMERGENCY);
        assertEquals(controlCommand.getStateInEmergency(), State.MOVING_UP);

        controlCommand.addRequest(request1);
        assertEquals(simulator.getModel().getState(), State.MOVING_UP);
        assertEquals(controlCommand.getStateInEmergency(), State.STOPPED);

        simulator.setState(State.STOPPED);
    }

    /**
     * Verification de ma méthode addRequest
     */
    @Test
    public void addRequestTest() {
        Request request = new Request(RequestType.GO_TO, 3);
        Request request1 = new Request(RequestType.GO_TO, 4);

        controlCommand.addRequest(null);
        assertNull(controlCommand.getCurrentRequest());
        assertEquals(controlCommand.getListRequest().size(), 0);

        controlCommand.addRequest(request);
        assertEquals(controlCommand.getCurrentRequest(), request);
        assertEquals(controlCommand.getListRequest().get(0), request);

        controlCommand.addRequest(request1);
        assertEquals(controlCommand.getCurrentRequest(), request);
        assertEquals(controlCommand.getListRequest().get(0), request);
        assertEquals(controlCommand.getListRequest().get(1), request1);
    }

    /**
     * Test une simple monté de l'ascenseur
     * @throws InterruptedException
     */
    @Test
    public void actionGotoTest() throws InterruptedException {
        Request request1 = new Request(RequestType.GO_TO, 4);

        controlCommand.addRequest(request1);

        controlCommand.start();
        simulator.start();

        Thread.sleep(13000);

        assertEquals(simulator.getModel().getPosition(), 4);
    }

    /**
     * Test un enchainement de monté et de descente
     * @throws InterruptedException
     */
    @Test
    public void actionGotoUpAndDownTest() throws InterruptedException {
        Request request1 = new Request(RequestType.GO_TO, 2);
        Request request2 = new Request(RequestType.GO_TO, 1);
        Request request3 = new Request(RequestType.GO_TO, 0);

        controlCommand.addRequest(request1);

        simulator.start();
        controlCommand.start();

        Thread.sleep(8000);
        //System.out.println("Fin sleep 1");
        assertEquals(simulator.getModel().getPosition(), 2);

        controlCommand.addRequest(request2);

        Thread.sleep(7000);
        //System.out.println("Fin sleep 2");
        assertEquals(simulator.getModel().getPosition(), 1);

        controlCommand.addRequest(request1);
        controlCommand.addRequest(request3);

        Thread.sleep(5000);
        //System.out.println("Fin sleep 3");
        assertEquals(simulator.getModel().getPosition(), 2);
        Thread.sleep(10000);
        //System.out.println("Fin sleep 4");
        assertEquals(simulator.getModel().getPosition(), 0);
    }

    /***
     * Test une monté et une descente avec des arrêt intermédiaire
     */
    @Test
    public void actionGotoWithIntermediateStopTest() throws InterruptedException {
        Request request1 = new Request(RequestType.GO_TO, 4);
        Request request2 = new Request(RequestType.OUTSIDE_UP, 1);
        Request request3 = new Request(RequestType.OUTSIDE_DOWN, 2);
        Request request4 = new Request(RequestType.OUTSIDE_UP, 3);
        Request request5 = new Request(RequestType.GO_TO, 0);

        controlCommand.addRequest(request1);
        controlCommand.addRequest(request2);
        controlCommand.addRequest(request3);
        controlCommand.addRequest(request4);
        controlCommand.addRequest(request5);

        simulator.start();
        controlCommand.start();

        assertEquals(controlCommand.getCurrentRequest(), request1);
        assertEquals(controlCommand.getListRequest().size(), 5);

        Thread.sleep(100);
        //System.out.println("Fin sleep 1");

        // Verification pendant le premier voyage entre RDC et 1

        assertEquals(controlCommand.getAction().getStopRequests().size(), 2);
        assertEquals(controlCommand.getAction().getStopRequests().get(0), request2);
        assertEquals(controlCommand.getAction().getStopRequests().get(1), request4);
        assertEquals(controlCommand.getCurrentRequest(), request2);
        assertEquals(controlCommand.getListRequest().size(), 5);
        Thread.sleep(7000);
        //System.out.println("Fin sleep 2");


        // Verification pendant le deuxième voyage entre 1 et 3

        assertEquals(controlCommand.getAction().getStopRequests().size(), 2);
        assertEquals(controlCommand.getAction().getStopRequests().get(0), request2);
        assertEquals(controlCommand.getAction().getStopRequests().get(1), request4);
        assertEquals(controlCommand.getCurrentRequest(), request4);
        assertEquals(controlCommand.getListRequest().size(), 4);
        Thread.sleep(9000);
        //System.out.println("Fin sleep 3");

        // Verification pendant le troisième voyage entre 3 et 4

        assertEquals(controlCommand.getAction().getStopRequests().size(), 2);
        assertEquals(controlCommand.getAction().getStopRequests().get(0), request2);
        assertEquals(controlCommand.getAction().getStopRequests().get(1), request4);
        assertEquals(controlCommand.getCurrentRequest(), request1);
        assertEquals(controlCommand.getListRequest().size(), 3);
        Thread.sleep(6000);
        //System.out.println("Fin sleep 4");

        // Verification pendant le quatrième voyage entre 4 et 2

        assertEquals(controlCommand.getAction().getStopRequests().size(), 1);
        assertEquals(controlCommand.getAction().getStopRequests().get(0), request3);
        assertEquals(controlCommand.getCurrentRequest(), request3);
        assertEquals(controlCommand.getListRequest().size(), 2);
        Thread.sleep(10000);
        //System.out.println("Fin sleep 5");

        // Verification pendant le cinquième voyage entre 2 et 0

        assertEquals(controlCommand.getAction().getStopRequests().size(), 1);
        assertEquals(controlCommand.getAction().getStopRequests().get(0), request3);
        assertEquals(controlCommand.getCurrentRequest(), request5);
        assertEquals(controlCommand.getListRequest().size(), 1);
        Thread.sleep(8000);
        System.out.println("Fin sleep 6");

        assertNull(controlCommand.getCurrentRequest());
        assertEquals(controlCommand.getListRequest().size(), 0);
    }

    /***
     * Test avec plusieurs monté et descente et plusieurs arrêt intermédiaire
     */
    @Test
    public void actionBestTest() {
        Request request1 = new Request(RequestType.OUTSIDE_UP, 3);
        Request request2 = new Request(RequestType.GO_TO, 4);

        Request request3 = new Request(RequestType.OUTSIDE_UP, 1);

        Request request4 = new Request(RequestType.OUTSIDE_DOWN, 2);
        Request request5 = new Request(RequestType.OUTSIDE_UP, 4);
        Request request6 = new Request(RequestType.OUTSIDE_DOWN, 4);

        Request request7 = new Request(RequestType.GO_TO, 5);

        controlCommand.addRequest(request1);
        controlCommand.addRequest(request2);
        controlCommand.addRequest(request3);
        controlCommand.addRequest(request4);
        controlCommand.addRequest(request5);

        simulator.start();
        controlCommand.start();
    }
}
