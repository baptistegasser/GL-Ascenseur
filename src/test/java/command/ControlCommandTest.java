package command;

import command.ControlCommand;
import command.FIFOStrategy;
import command.request.Request;
import command.request.RequestType;
import org.junit.jupiter.api.Test;
import simulator.ElevatorRemake;
import simulator.ElevatorSimulator;
import ui.model.ElevatorModel;

public class ControlCommandTest {

    @Test
    public void addRequestTest() {
        ElevatorRemake simulator = new ElevatorRemake(6,10);
        ControlCommand controlCommand = new ControlCommand(simulator, new FIFOStrategy(), simulator.getModel());


    }

    @Test
    public void actionTest() {
        try {
            ElevatorModel model = new ElevatorModel();
            ControlCommand controlCommand = new ControlCommand(new ElevatorRemake(6, 10), new FIFOStrategy(), model);

            Request request = new Request(RequestType.GO_TO, 5);
            Request request1 = new Request(RequestType.OUTSIDE_UP, 2);
            Request request2 = new Request(RequestType.OUTSIDE_DOWN, 3);
            Request request3 = new Request(RequestType.OUTSIDE_UP, 4);

            controlCommand.setCurrentRequest(request);
            controlCommand.getListRequest().add(request);
            controlCommand.getListRequest().add(request1);
            controlCommand.getListRequest().add(request2);
            controlCommand.getListRequest().add(request3);

            //controlCommand.action();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
