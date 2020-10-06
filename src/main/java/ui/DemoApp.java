package ui;

import ui.controller.DemoController;
import ui.view.DemoView;

import javax.swing.*;

/**
 * Une application de démonstration permettant de présenter le contrôleur commande.
 */
public class DemoApp {
    public final String WINDOW_TITLE = "GL-Elevator";

    /**
     * La fenêtre de l'application de démo
     */
    private JFrame window;

    public void start() {
        DemoView view = new DemoView();
        DemoController controller = new DemoController(view);

        window = new JFrame(WINDOW_TITLE);
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.add(view);
    }
}
