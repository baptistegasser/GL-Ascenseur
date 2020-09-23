package gui;

import javax.swing.*;
import java.awt.*;

public class GuiButtonInElevator extends JPanel {

    Button button1 = new Button("1");
    Button button2 = new Button("2");
    Button button3 = new Button("3");
    Button button4 = new Button("4");
    Button button5 = new Button("5");
    Button button6 = new Button("6");

    public GuiButtonInElevator(LayoutManager layout) {
        super(layout);

        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(button5);
        this.add(button6);

        //Ajouté le fonctionnement des boutons TODO
    }
}
