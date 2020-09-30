package gui;

import controller.ElevatorController;
import utils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class GuiElevator extends JPanel {
    ElevatorController controller;

    JPanel panelTop;
    JPanel panelCenter;
    JPanel panelBottom;

    JProgressBar progressBar;

    public GuiElevator(int nbFloor, ElevatorController controller) throws IOException {
        this.controller = controller;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        //this.setBorder(new EmptyBorder(10,0,0,0));

        panelTop = new JPanel(new FlowLayout());
        panelCenter = new JPanel(new FlowLayout());
        panelBottom = new JPanel(new FlowLayout());


        // Bouton pour appel vers le haut
        for (int i =0; i<nbFloor-1; i++) {
            JPanel paneButton = new JPanel();
            paneButton.setLayout(new BoxLayout(paneButton, BoxLayout.Y_AXIS));

            URL url = Utils.getResource("up-arrow.png");

            ImageIcon imageIcon = new ImageIcon(url);
            Image image = imageIcon.getImage();
            Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newimg);

            JButton button = new JButton(imageIcon);
            button.setPreferredSize(new Dimension(30 ,30));

            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Gui.isUrgency) return;

                    if (finalI == 0) System.out.println("Appel vers le haut du RDC");
                    else System.out.println("Appel vers le haut de l'étage : "+finalI);
                }
            });

            JLabel label;
            if (i == 0) label = new JLabel(" RDC");
            else label = new JLabel("   "+i);

            paneButton.add(button);
            paneButton.add(label);
            paneButton.setBorder(new EmptyBorder(0,50,0,0));

            panelTop.setBorder(new EmptyBorder(0,0,0,135));
            panelTop.add(paneButton);
        }

        //Bouton pour ppel vers le bas
        for (int i =0; i<nbFloor-1; i++) {
            JPanel paneButton = new JPanel();
            paneButton.setLayout(new BoxLayout(paneButton, BoxLayout.Y_AXIS));

            URL url = Utils.getResource("down-arrow.png");


            ImageIcon imageIcon = new ImageIcon(url);
            Image image = imageIcon.getImage();
            Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newimg);

            JButton button = new JButton(imageIcon);
            button.setPreferredSize(new Dimension(30 ,30));

            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Gui.isUrgency) return;

                    System.out.println("Appel vers le bas de l'étage : "+(finalI+1));
                }
            });

            JLabel label = new JLabel("   "+(i+1));

            paneButton.add(label);
            paneButton.add(button);
            paneButton.setBorder(new EmptyBorder(0,0,0,50)); // 50, 150


            panelBottom.setBorder(new EmptyBorder(0,135,0,0)); // 135, 350
            panelBottom.add(paneButton);
        }


        progressBar = new JProgressBar(0,100);
        progressBar.setMaximum((nbFloor-1)*10);
        progressBar.setPreferredSize(new Dimension(500, 20));
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        panelCenter.add(progressBar);

        panelTop.setAlignmentX(CENTER_ALIGNMENT);
        panelCenter.setAlignmentX(CENTER_ALIGNMENT);
        panelBottom.setAlignmentX(CENTER_ALIGNMENT);

        this.add(panelTop);
        //this.add(Box.createVerticalStrut(10));
        this.add(panelCenter);
        this.add(Box.createVerticalStrut(10));
        this.add(panelBottom);
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void updateElevator() {
        this.progressBar.setValue(controller.getModel().getCurrentFloor()*10);
    }
}
