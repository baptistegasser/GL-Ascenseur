package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GuiElevator extends JPanel {

    JPanel panelTop;
    JPanel panelCenter;
    JPanel panelBottom;

    public GuiElevator(int nbFloor) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        //this.setBorder(new EmptyBorder(10,0,0,0));

        panelTop = new JPanel(new FlowLayout());
        panelCenter = new JPanel(new FlowLayout());
        panelBottom = new JPanel(new FlowLayout());

        //this.setBackground(Color.BLACK);

        for (int i =0; i<nbFloor; i++) {
            JButton button = new JButton(new ImageIcon("image/down-arrow.png"));
            button.setPreferredSize(new Dimension(30 ,30));
            panelTop.add(button);
        }

        for (int i =0; i<nbFloor; i++) {
            JButton button = new JButton();

            button.setPreferredSize(new Dimension(30 ,30));
            panelBottom.add(button);
        }

        //System.out.println(System.getProperty("java.class.path","."));

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
