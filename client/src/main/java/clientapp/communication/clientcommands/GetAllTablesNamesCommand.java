package clientapp.communication.clientcommands;

import clientapp.communication.cervercommands.Message;
import clientapp.managedb.ContentController;

import java.util.List;

public class GetAllTablesNamesCommand implements Command{
    @Override
    public Message execute(ContentController controller) {
        List<String > result = controller.getAllTablesNames();
        Message message = new Message();
        message.setResultList(result);
        return message;
    }

    @Override
    public String getDescription() {
        return "Ask all table names";
    }
}
