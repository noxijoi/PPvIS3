package clientapp.communication.clientcommands;

import clientapp.communication.cervercommands.Message;
import clientapp.entity.Student;
import clientapp.managedb.ContentController;
import clientapp.managedb.TypeOfSelection;

import java.util.List;


public class DeleteStudentsCommand implements Command{
    private String tableName;
    private Object firstParam;
    private Object secondParam;
    private TypeOfSelection type;
    int pageSize;

    public DeleteStudentsCommand(Object firstParam, Object secondParam, TypeOfSelection type, String tableName, int pageSize) {
        this.firstParam = firstParam;
        this.secondParam = secondParam;
        this.type = type;
        this.tableName = tableName;
        this.pageSize = pageSize;
    }

    @Override
    public Message execute(ContentController controller) {
        Message message = new Message();
        List<Student> temp = controller.delStudentsByParam(firstParam,secondParam,type, tableName);
        controller.addAllStudents(temp, tableName+"deleted");
        message.setResultList(controller.getPage(pageSize,0,tableName+"deleted"));
        message.setRecordsNum(controller.getRecordsNum(tableName+"deleted"));
        return message;
    }

    @Override
    public String getDescription() {
        return "Delete students";
    }
}
