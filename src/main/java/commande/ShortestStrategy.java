package commande;

import java.util.List;

public class ShortestStrategy {
    public void execute (List<Integer> shortest) {
        int currentFloor = 1 ;
        int nextFloor= currentFloor;
        int newdelta;
        int oldDelta = 120;
        for (Integer integer: shortest) {
            if (currentFloor > integer) {
                newdelta = currentFloor - integer;
            }
            else {
                newdelta = integer - currentFloor;
            }
            if (newdelta > oldDelta) {
                nextFloor = integer;
            }
            oldDelta = newdelta;
        }
    }
}
