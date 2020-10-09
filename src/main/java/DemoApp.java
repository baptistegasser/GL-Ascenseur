import command.ControlCommand;
import command.ShortestStrategy;
import simulator.ElevatorSimulator;
import ui.controller.DemoController;
import ui.view.DemoView;

import javax.swing.*;

/**
 * Une application de démonstration permettant de présenter le contrôleur commande.
 */
public class DemoApp {
    /**
     * Le titre de la démo.
     */
    private static final String WINDOW_TITLE = "GL-Elevator";
    /**
     * La largeur original de la fenêtre de démo.
     */
    private static final int WINDOW_WIDTH = 800;
    /**
     * La hauteur original de la fenêtre de démo.
     */
    private static final int WINDOW_HEIGHT = 600;
    /**
     * Le nombre d'étage à simuler pour la démo.
     */
    private static final int FLOOR_COUNT = 6;
    /**
     * La vitesse (en seconde pour parcourir 1 étage) de l'ascenseur simulé.
     */
    private static final int SPEED = 2;

    /**
     * Fonction appelé pour lancer le programme.
     */
    public void start() {
        ElevatorSimulator simulator = new ElevatorSimulator(FLOOR_COUNT, SPEED);
        ControlCommand controlCommand = new ControlCommand(simulator, new ShortestStrategy(simulator.getModel()));
        controlCommand.start();
        DemoController controller = new DemoController(controlCommand);
        simulator.start();
        DemoView view = new DemoView(FLOOR_COUNT, WINDOW_WIDTH, controller, simulator.getModel());


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
