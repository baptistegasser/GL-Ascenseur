package ui.view;

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

    private final ElevatorStatePanel elevatorStatePanel;
    private final ElevatorInsidePanel elevatorInsidePanel;
    private final FloorAndProgressPanel floorAndProgressPanel;

    public DemoView(int nbFloor, int window_width, DemoController controller, ElevatorModel model) {
        super(new GridBagLayout());
        this.controller = controller;
        this.model = model;
        this.model.observe(this);

        this.elevatorStatePanel = new ElevatorStatePanel();
        this.elevatorInsidePanel = new ElevatorInsidePanel(nbFloor, controller);
        this.floorAndProgressPanel = new FloorAndProgressPanel(nbFloor, controller);

        // Créer une contrainte en grille
        GridBagConstraints constraints = new GridBagConstraints();

        // Ajout du panneau contenant l'affichage de l'état
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipady = 100;
        constraints.ipadx = 100;
        add(this.elevatorStatePanel, constraints);

        // Ajout du panneau affichant les boutons internes
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,150,20,0);
        constraints.ipady = 175;
        constraints.ipadx = 175;
        add(this.elevatorInsidePanel, constraints);

        // Ajout du panneau affichant la position et les boutons externes
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,0,0,0);
        constraints.gridwidth = 2; // Centre en utilisant les d2 colones pour le panneau
        constraints.ipady = 190;
        constraints.ipadx = window_width;
        add(this.floorAndProgressPanel, constraints);
    }

    @Override
    public void update(ElevatorModel val) {
        this.elevatorStatePanel.setState(val.state);
        this.floorAndProgressPanel.updateProgress(val.position);
    }
}
