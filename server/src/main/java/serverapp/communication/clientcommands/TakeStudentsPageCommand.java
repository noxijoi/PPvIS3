package serverapp.communication.clientcommands;


import serverapp.communication.cervercommands.Message;
import serverapp.entity.Student;
import serverapp.managedb.ContentController;

import java.util.List;

public class TakeStudentsPageCommand implements Command {
    private int pageSize;
    private int pageNum;
    private String tableName;

    public TakeStudentsPageCommand(int pageSize, int pageNum, String tableName) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.tableName = tableName;
    }

    @Override
    public Message execute(ContentController controller) {
        List<Student> resultList = controller.getPage(pageSize, pageNum, tableName);
        Message message = new Message();
        message.setResultList(resultList);
        message.setRecordsNum(controller.getRecordsNum(tableName));
        return message;
    }

    @Override
    public String getDescription() {
        return "Ask page of students";
    }
}
