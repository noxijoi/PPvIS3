package clientapp;

import clientapp.communication.cervercommands.Message;
import clientapp.communication.clientcommands.Command;
import clientapp.managedb.ContentController;

public class GetPageCommand implements Command {

    String name;
    int pageN;
    int recordsPerPage;
    public GetPageCommand(String name, int pageN, int recordsPerPage) {
        this.name = name;
        this.pageN = pageN;
        this.recordsPerPage = recordsPerPage;
    }

    @Override
    public Message execute(ContentController controller) {
        Message msg = new Message();

        msg.setResultList(controller.getPage(recordsPerPage, pageN, name));
        msg.setRecordsNum(controller.getRecordsNum(name));
        return msg;
    }

    @Override
    public String getDescription() {
        return "ask page " + pageN + "from " + name;
    }
}
