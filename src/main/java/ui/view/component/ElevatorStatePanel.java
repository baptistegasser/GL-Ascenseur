package ui.view.component;

import command.State;

import javax.swing.*;

/**
 * Une représentation textuelle de l'état actuel de l'ascenseur.
 */
public class ElevatorStatePanel extends JPanel {
    private final JLabel stateLbl;

    public ElevatorStatePanel() {
        this.stateLbl = new JLabel();
        this.add(stateLbl);

        setState(State.STOPPED);
    }

    /**
     * Met à jour l'état affiché de l'ascenseur.
     *
     * @param state le nouvelle état
     */
    public void setState(State state) {
        String display = "État de l'ascenseur: ";
        switch (state) {
            case STOPPED:
                display += "arrêté";
                break;
            case EMERGENCY:
                display += "en arrêt d'urgence";
                break;
            case MOVING_UP:
                display += "en monté";
                break;
            case MOVING_DOWN:
                display += "en descente";
                break;
            case MOVING_UP_STOP_NEXT:
                display += "en descente avec arrêt au prochain étage";
                break;
            case MOVING_DOWN_STOP_NEXT:
                display += "en monté avec arrêt au prochain étage";
                break;
        }
        this.stateLbl.setText(display);
    }
}
