package gui;

import controller.ElevatorController;

import javax.swing.*;
import java.awt.*;

public class GuiStatElevator extends JPanel {
    ElevatorController controller;

    JLabel label;

    public GuiStatElevator(LayoutManager layout, ElevatorController controller) {
        super(layout);
        this.controller = controller;

        label = new JLabel("État de l'ascenceur : Arrêt");

        this.add(label);
    }
}
