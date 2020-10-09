package ui.view.component;

import command.State;

import javax.swing.*;
import java.awt.*;

/**
 * Une représentation textuelle de l'état actuel de l'ascenseur.
 */
public class ElevatorStatePanel extends JPanel {
    /**
     * Le label qui affiche l'état
     */
    private final JLabel stateLbl;

    public ElevatorStatePanel() {
        this.setLayout(new GridLayout(0, 1, 0, 0));

        // On fixe la taille minimum de ce panneau à la taille de la plus grande string affiché en fonction du Font
        Dimension size = new Dimension(0, 0);
        FontMetrics metrics = this.getFontMetrics(this.getFont());
        size.width = metrics.stringWidth("En descente avec arrêt au prochain étage");
        this.setMinimumSize(size);
        this.setMaximumSize(size);

        JLabel info = new JLabel("État de l'ascenseur");
        info.setHorizontalAlignment(JLabel.CENTER);
        this.add(info);

        this.stateLbl = new JLabel();
        this.stateLbl.setHorizontalAlignment(JLabel.CENTER);
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
