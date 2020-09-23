package gui;

import javax.swing.*;
import java.awt.*;

public class GuiElevator extends JFrame {
    JPanel masterPane;

    JPanel elevatorPane;
    JPanel statElevator;
    JPanel listButtonElevator;

    Button button1 = new Button("1");
    Button button2 = new Button("2");
    Button button3 = new Button("3");
    Button button4 = new Button("4");
    Button button5 = new Button("5");
    Button button6 = new Button("6");


    public GuiElevator(String title) throws HeadlessException {
        super(title);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        generateMasterPane();

        this.setVisible(true);
    }

    public void generateMasterPane() {
        masterPane = new JPanel(new GridBagLayout());


        generateStatElevator();
        generateButtonElevator();
        generateElevatorPane();

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,0,0,0);
        constraints.gridheight = 2;
        constraints.ipady = 100;
        constraints.ipadx = 100;
        masterPane.add(statElevator, constraints);


        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,150,20,0);
        constraints.gridheight = 2;
        constraints.ipady = 175;
        constraints.ipadx = 175;
        masterPane.add(listButtonElevator, constraints);


        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(20,0,0,0);
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        constraints.ipady = 100;
        constraints.ipadx = 100;
        masterPane.add(elevatorPane, constraints);

        this.add(masterPane);
    }

    public void generateElevatorPane() {
        elevatorPane = new JPanel();

        Label label = new Label("elevator");

        elevatorPane.add(label);
    }

    public void generateStatElevator() {
        statElevator = new JPanel();

        Label label = new Label("État de l'ascenceur : ");

        statElevator.add(label);
    }

    public void generateButtonElevator() {
        listButtonElevator = new JPanel(new GridLayout(4,2));

        listButtonElevator.add(button1);
        listButtonElevator.add(button2);
        listButtonElevator.add(button3);
        listButtonElevator.add(button4);
        listButtonElevator.add(button5);
        listButtonElevator.add(button6);
    }
}
