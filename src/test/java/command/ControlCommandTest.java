package command;

import commande.ControlCommand;
import commande.FIFOStrategy;
import org.junit.jupiter.api.Test;
import simulator.ElevatorSimulator;
import ui.model.ElevatorModel;

public class ControlCommandTest {

    @Test
    public void addRequestTest() {
        ElevatorModel model = new ElevatorModel();
        ControlCommand controlCommand = new ControlCommand(new ElevatorSimulator(6,10,0,model), new FIFOStrategy(), model);


    }
}
