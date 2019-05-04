package serverapp.communication.clientcommands;


import serverapp.communication.cervercommands.Message;
import serverapp.managedb.ContentController;

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
