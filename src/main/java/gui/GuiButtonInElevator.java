package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiButtonInElevator extends JPanel {

    public GuiButtonInElevator(LayoutManager layout, int nbFloor) {
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
    }
}
