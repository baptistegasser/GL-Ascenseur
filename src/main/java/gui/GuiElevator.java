package gui;

import javax.swing.*;
import java.awt.*;

public class GuiElevator extends JPanel {
    JLabel label;

    public GuiElevator(LayoutManager layout) {
        super(layout);

        label = new JLabel("elevator");

        //Ajouté la barre representant l'ascenceur et les boutons de chaque étage TODO

        this.add(label);
    }
}
