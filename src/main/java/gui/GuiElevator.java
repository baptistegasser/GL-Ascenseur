package gui;

import utils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GuiElevator extends JPanel {

    JPanel panelTop;
    JPanel panelCenter;
    JPanel panelBottom;

    public GuiElevator(int nbFloor) throws IOException {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        //this.setBorder(new EmptyBorder(10,0,0,0));

        panelTop = new JPanel(new FlowLayout());
        panelCenter = new JPanel(new FlowLayout());
        panelBottom = new JPanel(new FlowLayout());


        // Bouton pour appel vers le haut
        for (int i =0; i<nbFloor-1; i++) {
            JPanel paneButton = new JPanel();

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
                    if (finalI == 0) System.out.println("Appel vers le haut du RDC");
                    else System.out.println("Appel vers le haut de l'étage : "+finalI);
                }
            });

            paneButton.add(button);
            paneButton.setBorder(new EmptyBorder(0,45,0,0));

            panelTop.setBorder(new EmptyBorder(0,0,0,135));
            panelTop.add(paneButton);
        }

        //Bouton pour ppel vers le bas
        for (int i =0; i<nbFloor-1; i++) {
            JPanel paneButton = new JPanel();

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
                    System.out.println("Appel vers le bas de l'étage : "+(finalI+1));
                }
            });

            paneButton.setBorder(new EmptyBorder(0,0,0,45));
            paneButton.add(button);

            panelBottom.setBorder(new EmptyBorder(0,135,0,0));
            panelBottom.add(paneButton);
        }

        JProgressBar progressBar = new JProgressBar(0,100);
        progressBar.setMaximum(nbFloor*10);
        progressBar.setPreferredSize(new Dimension(500, 20));
        progressBar.setValue(50);
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
}
