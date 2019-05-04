package clientapp.communication.clientcommands;

import clientapp.communication.cervercommands.Message;
import clientapp.managedb.ContentController;


public class ActivateTableCommand implements Command
{
    private  String tableName;
    int pageSize;

    public ActivateTableCommand(String tableName, int pageSize){
        this.tableName = tableName;
        this.pageSize = pageSize;
    }



    @Override
    public Message execute(ContentController controller) {
        Message message = new Message();
        message.setResultList(controller.getPage(pageSize,1, tableName));
        message.setRecordsNum(controller.getRecordsNum(tableName));
        message.setExecutionResult(true);
        return message;
    }

    @Override
    public String getDescription() {
        return "Activate table " + tableName;
    }
}
