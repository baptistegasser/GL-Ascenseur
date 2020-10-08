package ui;

import ui.controller.DemoController;
import ui.model.ElevatorModel;
import ui.view.DemoView;

import javax.swing.*;

/**
 * Une application de démonstration permettant de présenter le contrôleur commande.
 */
public class DemoApp {
    public static final String WINDOW_TITLE = "GL-Elevator";
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int FLOOR_COUNT = 6;

    /**
     * La fenêtre de l'application de démo
     */
    private JFrame window;

    public void start() {
        DemoController controller = new DemoController();
        // TODO simulator.getModel();
        DemoView view = new DemoView(FLOOR_COUNT, controller, new ElevatorModel());

        window = new JFrame(WINDOW_TITLE);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        window.add(view);
        window.setVisible(true);
    }

    // TODO Delete no push
    public static void main(String[] args) {
        new DemoApp().start();
    }
}