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
        this.model = new ElevatorModel();
    }

    public void goTo(double floor) {
        while (floor != ElevatorModel.position) {
            if (floor < ElevatorModel.position) {
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
        ++ElevatorModel.position;
    }

    public void goDown() {
        --ElevatorModel.position;
    }

    public double getPosition() {
        return position;
    }
}
