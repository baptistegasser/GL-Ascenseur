package simulator;

import command.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import command.model.ElevatorModel;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour le simulateur.
 */
class ElevatorSimulatorTest {
    private ElevatorSimulator simulator;
    private ElevatorModel model;

    @BeforeEach
    void setUp() {
        simulator = new ElevatorSimulator(8, 2);
        model = simulator.getModel();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        simulator.stop();
    }

    /**
     * Test de la méthode start qui ne doit pas changer l'état de l'ascenseur.
     */
    @Test
    void start() throws InterruptedException {
        ElevatorModel save = simulator.getModel();
        simulator.start();
        Thread.sleep(500);
        assertEquals(save, simulator.getModel());
    }

    /**
     * Vérification que la méthode stop fonctionne.
     */
    @Test
    void stop() throws InterruptedException {
        simulator.start();
        Thread.sleep(500);
        assertDoesNotThrow(() -> simulator.stop());
    }

    /**
     * Test le changement d'état.
     */
    @Test
    void setState() {
        // Check le model de base
        assertEquals(0, model.getPosition());
        assertEquals(8, model.nbFloor);
        assertEquals(State.STOPPED, model.state);

        simulator.setState(State.MOVING_UP);
        assertEquals(State.MOVING_UP, model.state);
    }

    /**
     * Test le changement d'état lorsque le simulateur est en marche.
     */
    @Test
    void setStateWhenRunning() {
        assertEquals(State.STOPPED, model.state);

        simulator.start();
        simulator.setState(State.MOVING_UP);
        assertEquals(State.MOVING_UP, model.state);

        simulator.setState(State.STOPPED);
        assertEquals(State.STOPPED, model.state);
    }

    /**
     * Test la montée de l'ascenseur.
     */
    @Test
    void assertGoUp() throws InterruptedException {
        assertEquals(0, model.getPosition());

        simulator.start();
        simulator.setState(State.MOVING_UP);
        Thread.sleep(1000);
        assertTrue(0.485 < model.getPosition() && 0.515 > model.getPosition(), "Position is out of range : " + model.getPosition());
        Thread.sleep(1000);
        assertTrue(0.9 < model.getPosition() && 1.1 > model.getPosition(), "Position is out of range : " + model.getPosition());
    }

    /**
     * S'assure que si l'ascenseur monte et doit s'arrêter au prochain étage qu'il le s'arrête.
     */
    @Test
    void willStopWillGoingUp() throws InterruptedException {
        model.setPosition(4);

        simulator.start();
        simulator.setState(State.MOVING_UP);
        Thread.sleep(100);

        simulator.setState(State.MOVING_UP_STOP_NEXT);
        Thread.sleep(2100);

        assertEquals(5, model.position);
        assertEquals(State.STOPPED, model.state);
    }

    /**
     * S'assure que si l'ascenseur monte trop haut, il s'arrête en urgence.
     */
    @Test
    void assertEmergencyStopIfTooHigh() throws InterruptedException {
        model.setPosition(7.75);

        simulator.start();
        simulator.setState(State.MOVING_UP);
        Thread.sleep(750);

        assertEquals(8.0, model.getPosition());
        assertEquals(State.EMERGENCY, model.state);
    }

    /**
     * Test la descente de l'ascenseur.
     */
    @Test
    void assertGoDown() throws InterruptedException {
        model.setPosition(5);

        simulator.start();
        simulator.setState(State.MOVING_DOWN);
        Thread.sleep(1000);
        assertTrue(4.475 < model.getPosition() && 4.505 > model.getPosition(), "Position is out of range : " + model.getPosition());
        Thread.sleep(1000);
        assertTrue(3.9 < model.getPosition() && 4.1 > model.getPosition(), "Position is out of range : " + model.getPosition());
    }

    /**
     * S'assure que si l'ascenseur descend et doit s'arrêter au prochain étage qu'il le s'arrête.
     */
    @Test
    void willStopWillGoingDown() throws InterruptedException {
        model.setPosition(4);

        simulator.start();
        simulator.setState(State.MOVING_DOWN);
        Thread.sleep(100);

        simulator.setState(State.MOVING_DOWN_STOP_NEXT);
        Thread.sleep(2100);

        assertEquals(3, model.position);
        assertEquals(State.STOPPED, model.state);
    }

    /**
     *
     * @throws InterruptedException
     */
    @Test
    void willGoDownAndStopOneFloor() throws InterruptedException {
        model.setPosition(3);

        simulator.start();
        simulator.setState(State.MOVING_DOWN_STOP_NEXT);
        Thread.sleep(2000);
        assertEquals(2, model.position);
        assertEquals(State.STOPPED, model.state);
    }

    /**
     * S'assure que si l'ascenseur descend trop bas, il s'arrête en urgence.
     */
    @Test
    void assertEmergencyStopIfTooLow() throws InterruptedException {
        model.setPosition(0.25);

        simulator.start();
        simulator.setState(State.MOVING_DOWN);
        Thread.sleep(750);

        assertEquals(0, model.getPosition());
        assertEquals(State.EMERGENCY, model.state);
    }

    /**
     * Test de l'arrêt de l'ascenseur.
     */
    @Test
    void assertStopDontMove() throws InterruptedException {
        model.setPosition(5);

        simulator.start();
        simulator.setState(State.MOVING_DOWN);
        Thread.sleep(500);

        simulator.setState(State.STOPPED);
        double pos = simulator.getModel().position;
        Thread.sleep(500);
        assertEquals(pos, simulator.getModel().position);
    }

    /**
     * S'assure que si l'on relance après une urgence tout cela fonctionne
     */
    @Test
    void assertRecoverFromEmergency() throws InterruptedException {
        model.setPosition(5);

        simulator.start();
        simulator.setState(State.MOVING_DOWN);

        // Wait and put in emergency
        Thread.sleep(1000);
        simulator.setState(State.EMERGENCY);
        model = simulator.getModel();

        // Wait again to check not moving anymore
        Thread.sleep(100);
        assertEquals(model, simulator.getModel());

        // Try to relaunch with going up and stopping as soon as possible
        simulator.setState(State.MOVING_UP_STOP_NEXT);

        // Wait one more time an check correctly moved again and stopped
        Thread.sleep(1200);
        assertEquals(State.STOPPED, model.state);
    }
}