package ui.view;

import gui.GuiButtonInElevator;
import ui.DemoApp;
import ui.controller.DemoController;
import ui.view.component.ElevatorInsidePanel;
import ui.view.component.ElevatorStatePanel;
import ui.view.component.FloorAndProgressPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * La vue de démonstration représentant de manière schématisé un ascenseur.
 * @see DemoController
 */
public class DemoView extends JPanel {
    /**
     * Le controller lié à cette vue
     */
    private DemoController controller;

    public DemoView(int nbFloor) {
        super(new GridBagLayout());

        // Contrainte appliqué sur le layout
        GridBagConstraints constraints = new GridBagConstraints();

        //Place l'élément à la case 0,0
        constraints.gridx = 0;
        constraints.gridy = 0;

        //Applique un padding sur l'élément (Surement à revoir) TODO
        constraints.insets = new Insets(0,0,0,0);

        //Taille de l'élèment
        constraints.ipady = 100;
        constraints.ipadx = 100;

        //Applique un élèment avec les contrainte appliqué
        add(new ElevatorStatePanel(), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,150,20,0);
        constraints.ipady = 175;
        constraints.ipadx = 175;
        add(new ElevatorInsidePanel(nbFloor), constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,0,0,0);

        //Permet ed centré l'élément su bas, il prend toute la ligne (les 2 colonnes pour être précis)
        constraints.gridwidth = 2;
        constraints.ipady = 190;
        constraints.ipadx = DemoApp.WINDOW_WIDTH;
        FloorAndProgressPanel panel = new FloorAndProgressPanel(nbFloor);
        add(panel, constraints);
    }

    public void setController(DemoController controller) {
        this.controller = controller;
    }
}
