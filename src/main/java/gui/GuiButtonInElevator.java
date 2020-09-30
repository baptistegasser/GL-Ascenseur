package gui;

import controller.ElevatorController;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class GuiButtonInElevator extends JPanel {
    ElevatorController controller;
    Popup popup;

    public GuiButtonInElevator(LayoutManager layout, int nbFloor, ElevatorController controller) throws IOException {
        super(layout);
        this.controller = controller;

        for (int i =0; i<nbFloor; i++) {

            JButton button = new JButton();

            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Gui.isUrgency) return;

                    try {
                        if (finalI == 0) {
                            controller.getModel().goTo(0);
                        } else controller.getModel().goTo(finalI);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
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

        JFrame popFrame = new JFrame("Urgence");
        popFrame.setSize(300, 250);
        popFrame.setResizable(false);
        popFrame.setLocationRelativeTo(null);

        PopupFactory pf = new PopupFactory();

        JPanel popPane = new JPanel();

        JButton buttonStopUrgence = new JButton("Stop urgence");

        popPane.add(buttonStopUrgence);
        popPane.setPreferredSize(new Dimension(150,100));
        popPane.setBackground(Color.GRAY);

        buttonStopUrgence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popFrame.dispose();
                Gui.isUrgency = false;
            }
        });

        popup = pf.getPopup(popFrame, popPane, 200, 150);

        JButton buttonUrgency = new JButton(imageIcon);
        buttonUrgency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Gui.isUrgency) return;

                System.out.println("URGENCE!!!");
                Gui.isUrgency = true;
                popup.show();
            }
        });

        this.add(buttonUrgency);

    }
}
