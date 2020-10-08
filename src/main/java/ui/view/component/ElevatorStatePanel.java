package ui.view.component;

import command.State;

import javax.swing.*;

/**
 * Une représentation textuelle de l'état actuel de l'ascenseur.
 */
public class ElevatorStatePanel extends JPanel {
    private final JLabel stateLbl;

    public ElevatorStatePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel("État de l'ascenseur"));
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
        String display = "display error";
        switch (state) {
            case STOPPED:
                display = "Arrêté";
                break;
            case EMERGENCY:
                display = "En arrêt d'urgence";
                break;
            case MOVING_UP:
                display = "En monté";
                break;
            case MOVING_DOWN:
                display = "En descente";
                break;
            case MOVING_UP_STOP_NEXT:
                display = "En monté avec arrêt au prochain étage";
                break;
            case MOVING_DOWN_STOP_NEXT:
                display = "En descente avec arrêt au prochain étage";
                break;
        }
        this.stateLbl.setText(display);
    }
}
