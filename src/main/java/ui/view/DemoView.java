package ui.view;

import command.State;
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
 *
 * @see DemoController
 */
public class DemoView extends JPanel implements Observer<ElevatorModel> {
    /**
     * Le panneau affichant l'état de l'ascenseur
     *
     * @see ElevatorStatePanel
     */
    private final ElevatorStatePanel elevatorStatePanel;
    /**
     * Le panneau affichant le panneau de boutons interne
     *
     * @see ElevatorInsidePanel
     */
    private final ElevatorInsidePanel elevatorInsidePanel;
    /**
     * Le panneau affichant les boutons externes et le progrès
     *
     * @see FloorAndProgressPanel
     */
    private final FloorAndProgressPanel floorAndProgressPanel;

    public DemoView(int nbFloor, int window_width, DemoController controller, ElevatorModel model) {
        super(new GridBagLayout());
        model.observe(this);

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
        constraints.insets = new Insets(0, 0, 20, 0);
        constraints.ipady = 100;
        constraints.ipadx = 100;
        add(this.elevatorInsidePanel, constraints);

        // Ajout du panneau affichant la position et les boutons externes
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridwidth = 2; // Centre en utilisant les d2 colones pour le panneau
        constraints.ipady = 190;
        constraints.ipadx = window_width;
        add(this.floorAndProgressPanel, constraints);
    }

    /**
     * Fonction appelé à chaque changement du modèle, permet de m-à-j la vue.
     *
     * @param model le nouveau model
     */
    @Override
    public void update(ElevatorModel model) {
        this.elevatorStatePanel.setState(model.state);
        this.floorAndProgressPanel.updateProgress(model.position);

        if (model.state == State.STOPPED) {
            elevatorInsidePanel.turnBacklightOff((int) model.position);
            floorAndProgressPanel.turnBacklightOff((int) model.position);
        }
    }
}
