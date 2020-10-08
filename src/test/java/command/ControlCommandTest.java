package command;

import command.request.Request;
import command.request.RequestType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulator.ElevatorRemake;

import static org.junit.jupiter.api.Assertions.*;

public class ControlCommandTest {

    ElevatorRemake simulator;
    ControlCommand controlCommand;

    @BeforeEach
    void setUp() {
        simulator = new ElevatorRemake(6,3);
        controlCommand = new ControlCommand(simulator, new FIFOStrategy());
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
}
