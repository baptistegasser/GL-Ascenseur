package ui.controller;

import ui.view.DemoView;

/**
 * Le contrôler chargé d'opérer la vue de démo
 * @see DemoView
 */
public class DemoController {
    /**
     * La vue contrôlé par ce contrôler
     */
    private final DemoView view;

    public DemoController(DemoView view) {
        this.view = view;
    }
}
