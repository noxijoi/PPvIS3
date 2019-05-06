package serverapp.managedb;

import lib.TypeOfSelection;
import lib.communication.ContentController;
import lib.entity.StudArray;
import lib.entity.Student;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Shell;

import java.util.List;


public class Controller {
    private Logger LOGGER = LogManager.getLogger(Controller.class);
    private Shell parent;
    private String currentTableName;
    private int currentPageSize = 10;
    private ContentController contentController;

    public Controller(Shell parent, String tableName, ContentController contentController) {
        this.parent = parent;
        this.currentTableName = tableName;
        this.contentController = contentController;
    }

    public String getCurrentTableName() {
        return currentTableName;
    }

    public List<String> askAllProgramLang() {
        return contentController.getAllProgrammingLanguages(currentTableName);
    }

    public List<String> askAllTablesNames() {
            return contentController.getAllTablesNames();

    }

    public List<Student> askPage(int currentPage) {
            return contentController.getPage(currentPageSize, currentPage, currentTableName);

    }


    public void askCreateStudentsTable(String tableName) {
            contentController.createStudentsTable(tableName);
    }

    public void askAddStudent(Student student) {
            contentController.addStudent(student,currentTableName);
    }

    public List<Student> askDelStudentsByParam(Object firstParam, Object secondParam, TypeOfSelection type) {
        contentController.clearTable(currentTableName+"deleted");
        List<Student> temp =contentController.delStudentsByParam(firstParam,secondParam,type, currentTableName);
        contentController.addAllStudents(temp, currentTableName+"deleted");
        return contentController.getPage(currentPageSize, 0, currentTableName+"deleted");
    }

    //FIX
    public void askDeleteTable(String currentTable) {
        contentController.dropStudArray(currentTable);
    }

    public int askChangeTable(String value) {
            currentTableName = value;
            return contentController.getRecordsNum(currentTableName);

    }

    public List<Student> askFindStudentByParam(Object firstParam, Object secondParam, TypeOfSelection type) {
        contentController.clearTable(currentTableName+"found");
        List<Student> temp = contentController.findStudentByParam(firstParam,secondParam,type, currentTableName);
        contentController.addAllStudents(temp, currentTableName+"found");
        return temp;
    }

    public void setCurrentPageSize(int numOfRecords) {
        currentPageSize = numOfRecords;
    }

    public ContentController getContentController() {
        return contentController;
    }

    public StudArray getCurrentStudArray() {
        return contentController.getStudArray(currentTableName);
    }

    public int askRecordsNum() {
        return contentController.getRecordsNum(currentTableName);
    }
}
