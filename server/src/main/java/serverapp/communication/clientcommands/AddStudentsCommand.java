package serverapp.communication.clientcommands;


import serverapp.communication.cervercommands.Message;
import serverapp.entity.Student;
import serverapp.managedb.ContentController;

import java.util.List;

public class AddStudentsCommand implements Command {

    private List<Student> students;
    private String tableName;

    public AddStudentsCommand(List<Student> students, String tableName) {
        this.students = students;
        this.tableName = tableName;
    }

    @Override
    public Message execute(ContentController controller) {
        controller.addAllStudents(students,tableName);
        Message message = new Message();
        message.setRecordsNum(controller.getRecordsNum(tableName));
        return message;
    }

    @Override
    public String getDescription() {
        return "Add " + students.size();
    }

}
