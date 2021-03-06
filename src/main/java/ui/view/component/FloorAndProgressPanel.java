package ui.view.component;

import ui.controller.DemoController;
import ui.model.Dir;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * Ce composant contient l'affichage de la position de l'ascenseur ainsi que les boutons
 * externes disponible à chaque étages.
 */
public class FloorAndProgressPanel extends JPanel {
    private final int nbFloor;
    private JProgressBar progressBar;
    /**
     * Stocke les panneau contenant les boutons d'appels afin de les accéder plus tard (backlight)
     */
    private final HashMap<String, JPanel> buttonPanels;
    /**
     * Le controller de la vue où ce trouve cette élément.
     */
    private final DemoController controller;

    public FloorAndProgressPanel(final int nbFloor, DemoController controller) {
        super();
        this.nbFloor = nbFloor;
        this.controller = controller;
        this.buttonPanels = new HashMap<>();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel panelTop = generateTopPanel();
        JPanel panelCenter = generateCenterPanel();
        JPanel panelBottom = generateBottomPanel();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(panelTop);
        panel.add(panelCenter);
        panel.add(panelBottom);

        JScrollPane root = new JScrollPane(panel);

        this.add(root);
    }

    /**
     * Créer le panneau contenant les boutons permettant d'appeler l'ascenseur vers le bas.
     *
     * @return le panneau correctement configuré
     */
    private JPanel generateTopPanel() {
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));

        for (int i = 0; i < nbFloor; i++) {
            JPanel paneButton = generateCallButton(Dir.UP, i);
            buttonPanels.put(Dir.UP.toString() + i, paneButton);
            panelTop.add(paneButton);
        }

        return panelTop;
    }

    /**
     * Créer le panneau central contenant la barre de progression correspondant à la position de l'ascenseur.
     *
     * @return le panneau correctement configuré
     */
    private JPanel generateCenterPanel() {
        JPanel panelCenter = new JPanel(new FlowLayout(FlowLayout.LEFT));

        progressBar = new JProgressBar(0, 100);
        progressBar.setMaximum(nbFloor * 10);
        // TODO fix width
        progressBar.setPreferredSize(new Dimension((nbFloor + 1) * 30 + nbFloor * 55, 20));
        progressBar.setValue(0);

        panelCenter.add(progressBar);
        return panelCenter;
    }

    /**
     * Créer le panneau contenant les boutons permettant d'appeler l'ascenseur vers le haut.
     *
     * @return le panneau correctement configuré
     */
    private JPanel generateBottomPanel() {
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel space = new JPanel();
        space.setMinimumSize(new Dimension(80, 0));
        space.setPreferredSize(new Dimension(80, 0));
        space.setMaximumSize(new Dimension(80, 0));
        panelBottom.add(space);

        for (int i = 1; i <= nbFloor; i++) {
            JPanel paneButton = generateCallButton(Dir.DOWN, i);
            buttonPanels.put(Dir.DOWN.toString() + i, paneButton);
            panelBottom.add(paneButton);
        }

        return panelBottom;
    }

    /**
     * Créer un bouton d'appel d'ascenseur en fonction de la direction et l'étage.
     *
     * @param dir   la direction souhaité par l'utilisateur qui cliquera
     * @param floor l'étage où ce situe le bouton
     * @return un container contenant le bouton
     */
    private JPanel generateCallButton(Dir dir, final int floor) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        Image img;
        try {
            URL url;
            if (dir == Dir.UP) {
                url = Utils.getResource("up-arrow.png");
            } else {
                url = Utils.getResource("down-arrow.png");
            }
            img = ImageIO.read(url).getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JButton button = new JButton(new ImageIcon(img));
        button.setPreferredSize(new Dimension(30, 30));
        button.setName("btn");

        button.addActionListener(e -> {
            button.setBackground(Color.ORANGE);
            controller.handleFloorRequestOutside(dir, floor);
        });

        JLabel label = new JLabel("   " + floor);
        if (floor == 0) {
            label.setText(" RDC");
        }

        container.add(button);
        container.add(label);
        container.setBorder(new EmptyBorder(0, 0, 0, 50));
        return container;
    }

    /**
     * Met à jour la position de l'ascenseur.
     *
     * @param floor la nouvelle position exprimé sous forme d'étage, les flottants sont possible (ie: étage 5.8)
     */
    public void updateProgress(double floor) {
        if (floor < 0 || floor > nbFloor) {
            return;
        }
        progressBar.setValue((int) (floor * 10));
    }

    /**
     * Retire la couleur des boutons précédemment activé.
     *
     * @param floor l'étage où l'on éteint les boutons
     */
    public void turnBacklightOff(int floor) {
        String up = Dir.UP.toString() + floor;
        String down = Dir.DOWN.toString() + floor;

        for (String name : new String[]{up, down}) {
            JPanel panel = buttonPanels.get(name);
            if (panel == null) continue;

            for (Component c : panel.getComponents()) {
                if (c != null && c.getName().equals("btn")) {
                    c.setBackground(null);
                    break;
                }
            }
        }
    }
}
