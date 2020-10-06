import commande.ControlCommand;
import controller.ElevatorController;
import gui.Gui;

import java.io.IOException;
import java.util.ConcurrentModificationException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello GL-Ascenseur");

        ControlCommand controlCommand = new ControlCommand();

        ElevatorController controller = new ElevatorController(controlCommand);
    }
}
