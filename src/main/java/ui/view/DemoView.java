package ui.view;

import ui.DemoApp;
import ui.controller.DemoController;
import ui.model.ElevatorModel;
import ui.view.component.ElevatorInsidePanel;
import ui.view.component.ElevatorStatePanel;
import ui.view.component.FloorAndProgressPanel;
import utils.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * La vue de démonstration représentant de manière schématisé un ascenseur.
 * @see DemoController
 */
public class DemoView extends JPanel implements Observer<ElevatorModel> {
    /**
     * Le controller lié à cette vue
     */
    private final DemoController controller;
    /**
     * Le modèle de l'ascenseur
     */
    private final ElevatorModel model;

    public DemoView(int nbFloor, DemoController controller, ElevatorModel model) {
        super(new GridBagLayout());
        this.controller = controller;
        this.model = model;
        this.model.observe(this);

        // Créer une contrainte en grille
        GridBagConstraints constraints = new GridBagConstraints();

        // Ajout du panneau contenant l'affichage de l'état
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipady = 100;
        constraints.ipadx = 100;
        add(new ElevatorStatePanel(), constraints);

        // Ajout du panneau affichant les boutons internes
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,150,20,0);
        constraints.ipady = 175;
        constraints.ipadx = 175;
        add(new ElevatorInsidePanel(nbFloor), constraints);

        // Ajout du panneau affichant la position et les boutons externes
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,0,0,0);
        constraints.gridwidth = 2; // Centre en utilisant les d2 colones pour le panneau
        constraints.ipady = 190;
        constraints.ipadx = DemoApp.WINDOW_WIDTH;
        add(new FloorAndProgressPanel(nbFloor), constraints);
    }

    @Override
    public void update(ElevatorModel old, ElevatorModel val) {
        System.out.println("Model update");
    }
}
