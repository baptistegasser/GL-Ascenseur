package ui.view.component;

import javax.swing.*;

/**
 * Une représentation textuelle de l'état actuel de l'ascenseur.
 */
public class ElevatorStatePanel extends JPanel {
    private final JLabel stateLbl;

    public ElevatorStatePanel() {
        stateLbl = new JLabel("État de l'ascenseur: Arrêt");

        this.add(stateLbl);
    }
}
