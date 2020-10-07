import commande.ControlCommand;
import controller.ElevatorController;
import gui.Gui;
import simulator.ElevatorSimulator;

import java.io.IOException;
import java.util.ConcurrentModificationException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello GL-Ascenseur");

        ElevatorSimulator simulator = new ElevatorSimulator(5, 20, 0);

        ControlCommand controlCommand = new ControlCommand(simulator);

        ElevatorController controller = new ElevatorController(controlCommand);
    }
}
