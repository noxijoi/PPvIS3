package clientapp;


import lib.TypeOfSelection;
import lib.communication.cervercommands.Message;
import lib.communication.clientcommands.*;
import lib.entity.Student;
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
    private ObjectOutputStream oos;
    private  ObjectInputStream ois;


    public Client(Socket socket, ClientApp app) throws IOException {
        this.socket = socket;
        this.app = app;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());

    }

    public void askAddStudent(Student student, String tableName) throws IOException {
        int newRecNum = 0;
        try {
            AddStudentsCommand command = new AddStudentsCommand(Collections.singletonList(student), tableName);
            oos.writeObject(command);
            Message result = (Message) ois.readObject();
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
            oos.writeObject(command);
            Message msg = (Message) ois.readObject();
            result = (List<String>) msg.getResultList();
        } catch (IOException e) {
            LOGGER.error("can't get program languages " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public Message askChangeTable(String tableName, int recPerPage) throws IOException {
        Message msg = new Message();
        try {
            ActivateTableCommand command = new ActivateTableCommand(tableName, recPerPage);
            oos.writeObject(command);
            msg = (Message) ois.readObject();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return msg;
    }

    public boolean askDropCurrentTable(String tableName) {
        boolean done;
        try {
            DropTableCommand command = new DropTableCommand(tableName);
            oos.writeObject(command);
            Message msg = (Message) ois.readObject();
            done = msg.getExecutionResult();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            done = false;
        }
        return done;
    }

    public List<String> askAllTablesNames(){
        List<String> result = new ArrayList<>();
        GetAllTablesNamesCommand command = new GetAllTablesNamesCommand();
        try {

            oos.writeObject(command);
            Message msg = (Message) ois.readObject();
            result = (List<String>) msg.getResultList();
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public void askCreateStudentsTable(String tableName) throws IOException {
        CreateTableCommand command = new CreateTableCommand(tableName);
        try {

            oos.writeObject(command);
            Message msg = (Message) ois.readObject();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public List<Student> askFindStudentByParam(Object firstParam, Object secondParam, TypeOfSelection type, String tableName, int pageSize)
            throws IOException {
        List<Student> result = new ArrayList<>();
        try {
            SearchStudentsCommand command = new SearchStudentsCommand(firstParam, secondParam, type, tableName, pageSize);
            oos.writeObject(command);
            Message msg = (Message) ois.readObject();
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
            oos.writeObject(command);
            Message msg = (Message) ois.readObject();
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
            oos.writeObject(command);
            Message msg = (Message) ois.readObject();
            result = (List<Student>) msg.getResultList();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public int askNumOfRecords(String currentTableName) throws IOException {
        int result = 0;
        try {
            GetRecordsNumCommand command = new GetRecordsNumCommand(currentTableName);
            oos.writeObject(command);
            Message msg = (Message) ois.readObject();
            result =  msg.getRecordsNum();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }
}

