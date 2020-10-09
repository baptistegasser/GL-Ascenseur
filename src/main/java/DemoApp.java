import command.ControlCommand;
import command.FIFOStrategy;
import command.ShortestStrategy;
import simulator.ElevatorRemake;
import ui.controller.DemoController;
import ui.view.DemoView;

import javax.swing.*;

/**
 * Une application de démonstration permettant de présenter le contrôleur commande.
 */
public class DemoApp {
    private static final String WINDOW_TITLE = "GL-Elevator";
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int FLOOR_COUNT = 6;
    private static final int SPEED = 3;

    /**
     * Fonction appelé pour lancer le programme.
     */
    public void start() {
        ElevatorRemake elevatorRemake = new ElevatorRemake(FLOOR_COUNT, SPEED);
        ControlCommand controlCommand = new ControlCommand(elevatorRemake, new ShortestStrategy(elevatorRemake.getModel()));
        controlCommand.start();
        DemoController controller = new DemoController(controlCommand);
        elevatorRemake.start();
        DemoView view = new DemoView(FLOOR_COUNT, WINDOW_WIDTH, controller, elevatorRemake.getModel());


        JFrame window = new JFrame(WINDOW_TITLE);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        window.add(view);
        window.setVisible(true);
    }

    /**
     * Point d'entrée de l'application de démo.
     *
     * @param args arguments du programme
     */
    public static void main(String[] args) {
        new DemoApp().start();
    }
}
