package gui;

import javax.swing.*;
import java.awt.*;

public class GuiElevator extends JFrame {
    Button button;

    public GuiElevator(String title) throws HeadlessException {
        super(title);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
}
