package controller;

import commande.ControlCommand;
import gui.Gui;
import model.Elevator;

import java.io.IOException;

public class ElevatorController {
    Gui view;
    Elevator model;
    ControlCommand controlCommand;

    final int nbFloor = 6;
    public ElevatorController(ControlCommand controlCommand) throws IOException {
        this.controlCommand = controlCommand;
        view = new Gui("GL-Elevator", this, nbFloor);
        model = new Elevator(this, nbFloor);
    }

    public Gui getView() {
        return view;
    }

    public Elevator getModel() {
        return model;
    }
}
