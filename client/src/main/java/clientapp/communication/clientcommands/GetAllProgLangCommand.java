package clientapp.communication.clientcommands;

import clientapp.communication.cervercommands.Message;
import clientapp.managedb.ContentController;

import java.util.List;

public class GetAllProgLangCommand implements Command {
    String tableName;

    public GetAllProgLangCommand(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public Message execute(ContentController controller) {
        List<String> progLangs = controller.getAllProgrammingLanguages(tableName);
        Message msg = new Message();
        msg.setResultList(progLangs);
        return msg;
    }

    @Override
    public String getDescription() {
        return "ask programinglang";
    }
}
