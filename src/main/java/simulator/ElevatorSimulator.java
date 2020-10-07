package simulator;

import ui.model.ElevatorModel;

public class ElevatorSimulator {
    int floorCount;
    int speed;
    double position;

    ElevatorModel model;

    public ElevatorSimulator(int floorCount, int speed, double position) {
        this.floorCount = floorCount;
        this.speed = speed;
        this.position = position;
        this.model = new ElevatorModel(0);
    }

    public void goTo(int floor) {
        while (floor != model.getCurrentFloor()) {
            if (floor < model.getCurrentFloor()) {
                goDown();
            } else {
                goUp();
            }
        }
        System.out.println("Arrivé");
    }

    public void goUp() {
        model.setCurrentFloor(model.getCurrentFloor()+1);
    }

    public void goDown() {
        model.setCurrentFloor(model.getCurrentFloor()-1);
    }
}
