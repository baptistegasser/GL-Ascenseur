package gui;

import controller.ElevatorController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Gui extends JFrame {
    JPanel masterPane;
    ElevatorController controller;

    GuiButtonInElevator guiButtonInElevator;
    GuiElevator guiElevator;
    GuiStatElevator guiStatElevator;

    int nbFloor;
    public static boolean isUrgency = false;

    public Gui(String title, ElevatorController controller, int nbFloor) throws IOException {
        super(title);
        this.nbFloor = nbFloor;
        this.controller = controller;
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        generateMasterPane();

        this.setVisible(true);
    }

    /***
     * Generate a Gui of elevator
     */
    public void generateMasterPane() throws IOException {
        masterPane = new JPanel(new GridBagLayout());

        // Contrainte appliqué sur le layout
        GridBagConstraints constraints = new GridBagConstraints();

        //Place l'élèment à la case 0,0
        constraints.gridx = 0;
        constraints.gridy = 0;

        //Applique un padding sur l'élément (Surement à revoir) TODO
        constraints.insets = new Insets(0,0,0,0);

        //Taille de l'élèment
        constraints.ipady = 100;
        constraints.ipadx = 100;

        //Applique un élèment avec les contrainte appliqué
        guiStatElevator = new GuiStatElevator(new BorderLayout(), controller);
        masterPane.add(guiStatElevator, constraints);


        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,150,20,0);
        constraints.ipady = 175;
        constraints.ipadx = 175;
        guiButtonInElevator = new GuiButtonInElevator(new GridLayout(4,2), nbFloor, controller);
        masterPane.add(guiButtonInElevator, constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,0,0,0);

        //Permet ed centré l'élément su bas, il prend toute la ligne (les 2 colonnes pour être précis)
        constraints.gridwidth = 2;
        constraints.ipady = 100;
        constraints.ipadx = 100;
        guiElevator = new GuiElevator(nbFloor, controller);
        masterPane.add(guiElevator, constraints);

        this.add(masterPane);
    }

    public void updateGui() throws Exception {
        guiElevator.updateElevator();


    }
}
