package ui.view.component;

import ui.controller.DemoController;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Ce composant contient l'affichage des boutons présent à l'intérieur de l'ascenseur
 */
public class ElevatorInsidePanel extends JPanel {
    /**
     * Le controller de la vue où ce trouve cette élément.
     */
    private final DemoController controller;

    public ElevatorInsidePanel(int nbFloor, DemoController controller) {
        super(new GridLayout(0, 3));
        this.controller = controller;

        JButton rdc = new JButton("RDC");
        rdc.addActionListener(e -> handleFloorRequest(0));
        this.add(rdc);
        this.add(new JPanel());

        ImageIcon imageIcon;
        try {
            URL url = Utils.getResource("notification.png");
            Image img = ImageIO.read(url).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JButton buttonUrgency = new JButton(imageIcon);
        buttonUrgency.addActionListener(e -> showEmergencyPopUp());

        this.add(buttonUrgency);

        for (int i = 1; i <= nbFloor; ++i) {
            JButton button = new JButton();

            final int floor = i;
            button.addActionListener(e -> handleFloorRequest(floor));

            if (i == 0) button.setText("RDC");
            else button.setText("" + (i));
            this.add(button);
        }
    }

    /**
     * Affiche une pop up qui bloque l'affichage jusqu'à ce qu'on la ferme
     */
    private void showEmergencyPopUp() {
        final String[] option = {"Retour à la normal"};
        JOptionPane.showOptionDialog(SwingUtilities.getWindowAncestor(this),
                "Arrêt d'urgence !",
                "Attention",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                option,
                option[0]
        );
        // TODO faire quelque chose ici quand on ferme
    }

    /**
     * Fonction appeler lors du click sur le bouton d'arrêt d'urgence.
     */
    private void handleEmergencyStop() {
        System.out.println("Emergency called");
    }

    /**
     * Fonction appeler lors du click sur un bouton d'étage.
     *
     * @param floor l'étage lié au bouton
     */
    private void handleFloorRequest(int floor) {
        System.out.println("user want floor " + floor);
    }

    /**
     * Change la couleur d'un composant en en orange ou la retire.
     *
     * @param on si vrai ajouter la couleur sinon la retirer
     * @param c le composant a colorier
     */
    private void setBacklight(boolean on, Component c) {
        if (on) {
            c.setBackground(Color.ORANGE);
        } else {
            c.setBackground(null);
        }
    }
}
