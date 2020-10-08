package simulator;

import ui.model.ElevatorModel;

public class ElevatorSimulator {
    int floorCount;
    int speed;
    double position;

    ElevatorModel model;

    public ElevatorSimulator(int floorCount, int speed, double position, ElevatorModel model) {
        this.floorCount = floorCount;
        this.speed = speed;
        this.position = position;
        this.model = model;
    }

    public void goTo(double floor) {
        while (floor != model.getPosition()) {
            if (floor < model.getPosition()) {
                System.out.println("GoDown");
                goDown();
            } else {
                System.out.println("GoUp");
                goUp();
            }
        }
        System.out.println("Arrivé");
    }

    public void goUp() {
        model.setPosition(model.getPosition()+1);
    }

    public void goDown() { model.setPosition(model.getPosition()-1);
    }

    public double getPosition() {
        return position;
    }
}
