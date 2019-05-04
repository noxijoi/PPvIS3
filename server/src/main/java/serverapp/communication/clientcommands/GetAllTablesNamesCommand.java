package serverapp.communication.clientcommands;

import serverapp.communication.cervercommands.Message;
import serverapp.managedb.ContentController;

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
