package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GuiElevator extends JPanel {

    public GuiElevator() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setBorder(new EmptyBorder(10,0,0,0));

        //Ajouté la barre representant l'ascenceur et les boutons de chaque étage TODO

        //this.setBackground(Color.BLACK);

        JButton buttonTop = new JButton("Button top");
        JButton buttonBottom = new JButton("Button Bottom");

        JProgressBar progressBar = new JProgressBar(0,100);
        progressBar.setPreferredSize(new Dimension(500, 20));
        progressBar.setValue(50);
        progressBar.setStringPainted(true);

        buttonTop.setAlignmentX(CENTER_ALIGNMENT);
        progressBar.setAlignmentX(CENTER_ALIGNMENT);
        buttonBottom.setAlignmentX(CENTER_ALIGNMENT);

        this.add(buttonTop);
        this.add(Box.createVerticalStrut(10));
        this.add(progressBar);
        this.add(Box.createVerticalStrut(10));
        this.add(buttonBottom);
    }
}
