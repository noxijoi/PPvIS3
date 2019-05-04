package clientapp.communication.clientcommands;


import clientapp.communication.cervercommands.Message;
import clientapp.entity.Student;
import clientapp.managedb.ContentController;
import clientapp.managedb.TypeOfSelection;

import java.util.List;

public class SearchStudentsCommand implements Command{
    private String tableName;
    private Object firstParam;
    private Object secondParam;
    private TypeOfSelection type;
    private int pageSize;

    public SearchStudentsCommand(Object firstParam, Object secondParam, TypeOfSelection type, String tableName, int pageSize){
        this.firstParam = firstParam;
        this.secondParam = secondParam;
        this.type = type;
        this.tableName = tableName;
        this.pageSize = pageSize;
    }

    @Override
    public Message execute(ContentController controller) {
        Message message = new Message();
        List<Student> temp = controller.findStudentByParam(firstParam, secondParam, type, tableName);
        controller.addAllStudents(temp, tableName+"found");
        message.setResultList(controller.getPage(pageSize,0,tableName+"found"));
        message.setRecordsNum(controller.getRecordsNum(tableName+"found"));
        return message;
    }

    @Override
    public String getDescription() {
        return "search stuents by params";
    }
}
