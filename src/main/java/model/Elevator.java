package model;

import controller.ElevatorController;

public class Elevator {
    ElevatorController controller;

    int currentFloor;
    int nbFloor;

    static boolean threadUse = false;

    public Elevator(ElevatorController controller, int nbFloor) {
        this.controller = controller;
        this.nbFloor = nbFloor;

        currentFloor = 0;
    }

    public void goTo(int targetFloor) throws Exception {

        if (!threadUse) {
            threadUse = true;
            //Thread qui augmente la barre
            new Thread(new Runnable() {
                public void run() {
                    while (targetFloor != currentFloor) {
                        if (targetFloor < currentFloor) goDown();
                        else goUp();

                        System.out.println("Traitement débuté");

                        try {
                            controller.getView().updateGui();
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Traitement terminé");
                    threadUse = false;
                }
            }).start();
        }
    }

    public void goUp() {
        if (currentFloor == nbFloor) System.out.println("Trop haut");
        else currentFloor += 1;
    }

    public void goDown() {
        if (currentFloor == 0) System.out.println("Trop bas");
        else currentFloor -= 1;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
}
