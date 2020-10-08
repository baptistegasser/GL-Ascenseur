package simulator;

import command.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.model.ElevatorModel;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour le simulateur.
 */
class ElevatorRemakeTest {
    private ElevatorRemake simulator;

    @BeforeEach
    void setUp() {
        simulator = new ElevatorRemake(8, 2);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        simulator.stop();
    }

    @Test
    void setState() {
        // Check le model de base
        ElevatorModel model = simulator.getModel();
        assertEquals(0, model.getPosition());
        assertEquals(8, model.nbFloor);
        assertEquals(State.STOPPED, model.state);

        simulator.setState(State.MOVING_UP);
        assertEquals(State.MOVING_UP, model.state);
    }

    @Test
    void setStateWhenRunning() {
        ElevatorModel model = simulator.getModel();
        assertEquals(State.STOPPED, model.state);

        simulator.start();
        simulator.setState(State.MOVING_UP);
        assertEquals(State.MOVING_UP, model.state);

        simulator.setState(State.STOPPED);
        assertEquals(State.STOPPED, model.state);
    }

    @Test
    void assertGoUp() throws InterruptedException {
        ElevatorModel model = simulator.getModel();
        assertEquals(0, model.getPosition());

        simulator.start();
        simulator.setState(State.MOVING_UP);
        Thread.sleep(1000);
        assertTrue(0.485 < model.getPosition() && 0.515 > model.getPosition(), "Position is out of range : " + model.getPosition());
        Thread.sleep(1000);
        assertTrue(0.9 < model.getPosition() && 1.1 > model.getPosition(), "Position is out of range : " + model.getPosition());
    }

    @Test
    void assertGoDown() throws InterruptedException {
        ElevatorModel model = simulator.getModel();
        model.setPosition(5);
        assertEquals(5, model.getPosition());

        simulator.start();
        simulator.setState(State.MOVING_DOWN);
        Thread.sleep(1000);
        assertTrue(4.475 < model.getPosition() && 4.505 > model.getPosition(), "Position is out of range : " + model.getPosition());
        Thread.sleep(1000);
        assertTrue(3.9 < model.getPosition() && 4.1 > model.getPosition(), "Position is out of range : " + model.getPosition());

        /**
         * - Arrêt auto au derniers étages où arrêt d'urgence
         * - Arrêt auto si arrêt au prochain puis passage en mode stoppé
         */
    }

    @Test
    void assertEmergencyStopIfTooLow() throws InterruptedException {
        ElevatorModel model = simulator.getModel();
        model.setPosition(0.25);

        simulator.start();
        simulator.setState(State.MOVING_DOWN);
        Thread.sleep(500);

        assertEquals(0, model.getPosition());
        assertEquals(State.EMERGENCY, model.state);
    }

    @Test
    void assertEmergencyStopIfTooHigh() throws InterruptedException {
        ElevatorModel model = simulator.getModel();
        model.setPosition(7.75);

        simulator.start();
        simulator.setState(State.MOVING_UP);
        Thread.sleep(750);

        assertEquals(8.0, model.getPosition());
        assertEquals(State.EMERGENCY, model.state);
    }

    @Test
    void assertStopDontMove() throws InterruptedException {
        ElevatorModel model = simulator.getModel();
        model.setPosition(5);
        assertEquals(5, model.getPosition());

        simulator.start();
        simulator.setState(State.MOVING_DOWN);
        Thread.sleep(500);

        simulator.setState(State.STOPPED);
        double pos = simulator.getModel().position;
        Thread.sleep(500);
        assertEquals(pos, simulator.getModel().position);
    }

    @Test
    void start() throws InterruptedException {
        ElevatorModel save = simulator.getModel();
        simulator.start();
        Thread.sleep(500);
        assertEquals(save, simulator.getModel());
    }

    @Test
    void stop() throws InterruptedException {
        simulator.start();
        Thread.sleep(500);
        assertDoesNotThrow(() -> simulator.stop());
    }
}