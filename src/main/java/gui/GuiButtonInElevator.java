package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class GuiButtonInElevator extends JPanel {

    public GuiButtonInElevator(LayoutManager layout, int nbFloor) throws IOException {
        super(layout);

        for (int i =0; i<nbFloor; i++) {

            JButton button = new JButton();

            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (finalI == 0)  System.out.println("Go au RDC");
                    else System.out.println("Go a l'étage : "+(finalI));
                }
            });

            if (i == 0) button.setText("RDC");
            else button.setText(""+(i));
            this.add(button);
        }

        URL url = Utils.getResource("notification.png");

        ImageIcon imageIcon = new ImageIcon(url);
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);

        JButton buttonUrgency = new JButton(imageIcon);
        buttonUrgency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("URGENCE!!!");
            }
        });

        this.add(buttonUrgency);

    }
}
