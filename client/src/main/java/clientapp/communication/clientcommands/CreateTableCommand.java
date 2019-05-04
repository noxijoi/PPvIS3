package clientapp.communication.clientcommands;


import clientapp.communication.cervercommands.Message;
import clientapp.managedb.ContentController;

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
