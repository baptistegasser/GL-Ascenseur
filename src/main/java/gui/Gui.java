package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Gui extends JFrame {
    JPanel masterPane;

    final int nbFloor = 6;

    public Gui(String title) {
        super(title);
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
    public void generateMasterPane() {
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
        masterPane.add(new GuiStatElevator(new BorderLayout()), constraints);


        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,150,20,0);
        constraints.ipady = 175;
        constraints.ipadx = 175;
        masterPane.add(new GuiButtonInElevator(new GridLayout(4,2)), constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,0,0,0);

        //Permet ed centré l'élément su bas, il prend toute la ligne (les 2 colonnes pour être précis)
        constraints.gridwidth = 2;
        constraints.ipady = 100;
        constraints.ipadx = 100;
        masterPane.add(new GuiElevator(nbFloor), constraints);

        this.add(masterPane);
    }
}
