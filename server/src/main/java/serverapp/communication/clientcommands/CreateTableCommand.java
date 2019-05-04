package serverapp.communication.clientcommands;


import serverapp.communication.cervercommands.Message;
import serverapp.managedb.ContentController;

public class CreateTableCommand implements Command {
    String tableName;

    @Override
    public Message execute(ContentController controller) {
        Message message = new Message();
        message.setExecutionResult(controller.createStudentsTable(tableName));
        return message;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public CreateTableCommand(String tableName) {
        this.tableName = tableName;
    }


}
