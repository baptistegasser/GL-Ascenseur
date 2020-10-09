package ui.view.component;

import ui.controller.DemoController;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * Ce composant contient l'affichage des boutons présent à l'intérieur de l'ascenseur
 */
public class ElevatorInsidePanel extends JPanel {
    /**
     * Le controller de la vue où ce trouve cette élément.
     */
    private final DemoController controller;
    /**
     * Stocke les boutons d'appels afin de les accéder plus tard (backlight)
     */
    private final HashMap<Integer, JButton> buttons;

    public ElevatorInsidePanel(int nbFloor, DemoController controller) {
        super(new GridLayout(0, 3));
        this.controller = controller;
        this.buttons = new HashMap<>();

        JButton rdc = new JButton("RDC");
        rdc.addActionListener(e -> controller.handleFloorRequestInside(0));
        this.add(rdc);
        this.add(new JPanel());
        buttons.put(0, rdc);

        ImageIcon imageIcon;
        try {
            URL url = Utils.getResource("notification.png");
            Image img = ImageIO.read(url).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JButton buttonUrgency = new JButton(imageIcon);
        buttonUrgency.addActionListener(e -> {
            buttonUrgency.setBackground(Color.ORANGE);
            showEmergencyPopUp();
            buttonUrgency.setBackground(null);
        });

        this.add(buttonUrgency);

        for (int i = 1; i <= nbFloor; ++i) {
            JButton button = new JButton();

            final int floor = i;
            button.addActionListener(e -> {
                button.setBackground(Color.ORANGE);
                controller.handleFloorRequestInside(floor);
            });

            if (i == 0) button.setText("RDC");
            else button.setText("" + (i));
            this.add(button);
            buttons.put(i, button);
        }
    }

    /**
     * Affiche une pop up qui bloque l'affichage jusqu'à ce qu'on la ferme
     */
    private void showEmergencyPopUp() {
        controller.handleEmergencyStopRequest();
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
        controller.handleEmergencyStopExit();
    }

    /**
     * Retire la couleur d'un bouton précédemment activé.
     */
    public void turnBacklightOff(int floor) {
        JButton btn = buttons.get(floor);
        if (btn != null) {
            btn.setBackground(null);
        }
    }
}
