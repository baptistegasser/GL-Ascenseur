package ui.view;

import ui.controller.DemoController;

import javax.swing.*;

/**
 * La vue de démonstration représentant de manière schématisé un ascenseur.
 * @see DemoController
 */
public class DemoView extends JPanel {
    /**
     * Le controller lié à cette vue
     */
    private DemoController controller;

    public void setController(DemoController controller) {
        this.controller = controller;
    }
}
