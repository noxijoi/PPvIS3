package clientapp;

import clientapp.communication.cervercommands.Message;
import clientapp.communication.clientcommands.*;
import clientapp.managedb.TypeOfSelection;
import clientapp.entity.Student;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client {
    private final static Logger LOGGER = LogManager.getLogger(Client.class);

    private Socket socket;
    private ClientApp app;
    private ObjectInputStream objInStream;
    private ObjectOutputStream objOutStream;

    public Client(Socket socket, ClientApp app) throws IOException {
        this.socket = socket;
        this.app = app;
        this.objInStream = new ObjectInputStream(socket.getInputStream());
        this.objOutStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void askAddStudent(Student student, String tableName) throws IOException {
        int newRecNum = 0;
        try {
            AddStudentsCommand command = new AddStudentsCommand(Collections.singletonList(student), tableName);
            objOutStream.writeObject(command);
            Message result = (Message) objInStream.readObject();
            newRecNum = result.getRecordsNum();

        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        app.updateRecordsNum(newRecNum);
    }

    public List<String> askAllProgramLang(String tableName) {
        List<String> result = new ArrayList<>();
        try {
            GetAllProgLangCommand command = new GetAllProgLangCommand(tableName);
            objOutStream.writeObject(command);
            Message msg = (Message) objInStream.readObject();
            result = (List<String>) msg.getResultList();
        } catch (IOException e) {
            LOGGER.error("can't get program languages " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public int askChangeTable(String tableName, int recPerPage) throws IOException {
        int result = 0;
        try {
            ActivateTableCommand command = new ActivateTableCommand(tableName, recPerPage);
            objOutStream.writeObject(command);
            Message msg = (Message) objInStream.readObject();
            result = msg.getRecordsNum();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public boolean askDropCurrentTable(String tableName) {
        boolean done;
        try {
            DropTableCommand command = new DropTableCommand(tableName);
            objOutStream.writeObject(command);
            Message msg = (Message) objInStream.readObject();
            done = msg.getExecutionResult();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            done = false;
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            done = false;
        }
        return done;
    }

    public List<String> askAllTablesNames(){
        List<String> result = new ArrayList<>();
        GetAllTablesNamesCommand command = new GetAllTablesNamesCommand();
        try {
            objOutStream.writeObject(command);
            Message msg = (Message) objInStream.readObject();
            result = (List<String>) msg.getResultList();
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public void askCreateStudentsTable(String tableName) throws IOException {
        CreateTableCommand command = new CreateTableCommand(tableName);
        try {
            objOutStream.writeObject(command);
            Message msg = (Message) objInStream.readObject();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public List<Student> askFindStudentByParam(Object firstParam, Object secondParam, TypeOfSelection type, String tableName, int pageSize)
            throws IOException {
        List<Student> result = new ArrayList<>();
        try {
            CreateTableCommand createTableCommand = new CreateTableCommand(tableName + "found");
            objOutStream.writeObject(createTableCommand);
            objInStream.readObject();

            SearchStudentsCommand command = new SearchStudentsCommand(firstParam, secondParam, type, tableName, pageSize);
            objOutStream.writeObject(command);
            Message msg = (Message) objInStream.readObject();
            result = (List<Student>) msg.getResultList();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public List<Student> askDelStudentsByParam(Object firstParam, Object secondParam, TypeOfSelection type, String tableName, int pageSize)
            throws IOException {
        List<Student> result = new ArrayList<>();
        try {
            DeleteStudentsCommand command = new DeleteStudentsCommand(firstParam, secondParam, type, tableName, pageSize);
            objOutStream.writeObject(command);
            Message msg = (Message) objInStream.readObject();
            result = (List<Student>) msg.getResultList();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public List<Student> askPage(String name, int pageN, int recordsPerPage) throws IOException {
        List<Student> result = new ArrayList<>();
        try {
            GetPageCommand command = new GetPageCommand(name, pageN, recordsPerPage);
            objOutStream.writeObject(command);
            Message msg = (Message) objInStream.readObject();
            result = (List<Student>) msg.getResultList();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }
}