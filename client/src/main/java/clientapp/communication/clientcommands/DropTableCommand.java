package clientapp.communication.clientcommands;

import clientapp.communication.cervercommands.Message;
import clientapp.managedb.ContentController;

public class DropTableCommand implements Command {
    String tableName;
    public DropTableCommand(String tableName){
        this.tableName = tableName;
    }
    @Override
    public Message execute(ContentController controller) {
        boolean done = controller.dropStudArray(tableName);
        Message msg = new Message();
        msg.setExecutionResult(done);
        return msg;
    }

    @Override
    public String getDescription() {
        return "Delete table " + tableName;
    }
}
