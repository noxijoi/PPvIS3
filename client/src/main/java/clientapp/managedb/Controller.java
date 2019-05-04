package clientapp.managedb;

import clientapp.Client;
import clientapp.entity.Student;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class Controller {
    private Logger LOGGER = LogManager.getLogger(Controller.class);
    private Client client;
    private Shell parent;
    private String currentTableName;
    private int currentPageSize = 10;

    public Controller(Client client, Shell parent, String tableName) {
        this.client = client;
        this.parent = parent;
        this.currentTableName = tableName;
    }

    public String getCurrentTableName() {
        return currentTableName;
    }

    public List<String> askAllProgramLang() {
        return client.askAllProgramLang(currentTableName);
    }

    public List<String> askAllTablesNames() {
            return client.askAllTablesNames();

    }

    public List<Student> askPage( int currentPage) {
        try {
            return client.askPage(currentTableName, currentPage, currentPageSize);
        } catch (IOException e) {
            LOGGER.error("Can't read object from server" + e.getMessage());
            showMessageBox("Can't read object from server");
        }
        return Collections.EMPTY_LIST;
    }

    private void showMessageBox(String s) {
        MessageBox mb= new MessageBox(parent);
        mb.setMessage(s);
        mb.open();
    }

    public void askCreateStudentsTable(String tableName) {
        try {
            client.askCreateStudentsTable(tableName);
        } catch (IOException e) {
            LOGGER.error("Can't read object from server" + e.getMessage());
            showMessageBox("Can't read object from server");
        }
    }

    public void askAddStudent(Student student) {
        try {
            client.askAddStudent(student,currentTableName);
        } catch (IOException e) {
            LOGGER.error("Can't read object from server" + e.getMessage());
            showMessageBox("Can't read object from server");
        }
    }

    public List<Student> askDelStudentsByParam(Object firstParam, Object secondParam, TypeOfSelection type) {
        try {
            return client.askDelStudentsByParam(firstParam,secondParam,type, currentTableName, currentPageSize);
        } catch (IOException e) {
            LOGGER.error("Can't read object from server" + e.getMessage());
            showMessageBox("Can't read object from server");
            return Collections.EMPTY_LIST;
        }
    }

    //FIX
    public void askDeleteTable(String currentTable) {
        client.askDropCurrentTable(currentTable);
    }

    public int askChangeTable(String value) {
        int recNum =0;
        try {
            recNum = client.askChangeTable(value, currentPageSize);
            currentTableName = value;
        } catch (IOException e) {
            LOGGER.error("Can't read object from server" + e.getMessage());
            showMessageBox("Can't read object from server");
        }
        return recNum;
    }

    public List<Student> askFindStudentByParam(Object firstParam, Object secondParam, TypeOfSelection type) {
        try {
            return client.askFindStudentByParam(firstParam, secondParam, type, currentTableName, currentPageSize);
        } catch (IOException e) {
            LOGGER.error("Can't read object from server" + e.getMessage());
            showMessageBox("Can't read object from server");
            return Collections.EMPTY_LIST;
        }
    }

    public Client getClient() {
        return client;
    }

    public void setCurrentPageSize(int numOfRecords) {
        currentPageSize = numOfRecords;
    }
}
