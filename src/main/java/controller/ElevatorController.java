package controller;

import gui.Gui;
import model.Elevator;

import java.io.IOException;

public class ElevatorController {
    Gui view;
    Elevator model;

    final int nbFloor = 6;
    public ElevatorController() throws IOException {
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
