package gui;

import javax.swing.*;
import java.awt.*;

public class GuiStatElevator extends JPanel {
    JLabel label;

    public GuiStatElevator(LayoutManager layout) {
        super(layout);

        label = new JLabel("État de l'ascenceur : Arrêt");

        this.add(label);
    }
}
