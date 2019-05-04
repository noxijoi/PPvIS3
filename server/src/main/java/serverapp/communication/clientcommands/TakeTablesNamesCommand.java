package serverapp.communication.clientcommands;


import serverapp.communication.cervercommands.Message;
import serverapp.managedb.ContentController;

import java.util.Arrays;

public class TakeTablesNamesCommand implements Command {
    @Override
    public Message execute(ContentController controller) {
        Message message = new Message();
        message.setResultList(Arrays.asList(controller.getAllTablesNames()));
        return message;
    }

    @Override
    public String getDescription() {
        return "Ask table names";
    }
}
