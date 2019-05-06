package serverapp.xml.open;

import lib.entity.Name;
import lib.entity.StudArray;
import lib.entity.Student;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class StudentHandler extends DefaultHandler {
    private StudArray studArray;

    StudArray getStudentList() {
        return studArray;
    }

    private final String STUD_ARRAY = "stud-array";
    private final String STUD_ARRAY_NAME = "stud-array-name";
    private final String STUDENTS = "students";
    private final String NAME = "name";
    private final String STUDENT = "student";
    private final String FIRST_NAME = "first-name";
    private final String LAST_NAME = "last-name";
    private final String PATRONYMIC = "patronymic";
    private final String COURSE = "course";
    private final String GROUP_NUMBER = "group-number";
    private final String TOTAL_NUM_OF_TASK = "total-num-of-tasks";
    private final String NUM_OF_DONE_TASKS = "num-of-done-tasks";
    private final String PROGRAMMING_LANGUAGE = "programming-language";

    private String data;

    private String studArrayName;
    private List<Student> studentList;
    private Student student;
    private Name name;
    private String fName;
    private String lName;
    private String patronymic;
    private int course;
    private int groupNumber;
    private int totalNumOfTasks;
    private int numOfDoneTasks;
    private String programmingLanguage;

    StudentHandler() {
        super();
    }

    @Override
    public void startDocument() {
        studArray = new StudArray();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case STUD_ARRAY:
                studArray = new StudArray();
                break;
            case STUDENTS:
                studentList = new ArrayList<>();
                break;
            case STUDENT:
                student = new Student();
                break;
            case NAME:
                name = new Name();
                break;
            default:
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case STUDENTS:
                studArray.setStudents(studentList);
                studArray.setName(studArrayName);
                break;
            case STUD_ARRAY_NAME:
                studArrayName = data;
                break;
            case STUDENT:
                student.setCourse(course);
                student.setName(name);
                student.setGroupNumber(groupNumber);
                student.setNumOfDoneTasks(numOfDoneTasks);
                student.setProgrammingLanguage(programmingLanguage);
                student.setTotalNumOfTask(totalNumOfTasks);
                studentList.add(student);
                break;
            case NAME:
                name.firstName = fName;
                name.lastName = lName;
                name.patronymic = patronymic;
                break;
            case FIRST_NAME:
                fName = data;
                break;
            case LAST_NAME:
                lName = data;
                break;
            case PATRONYMIC:
                patronymic = data;
                break;
            case COURSE:
                course = Integer.parseInt(data);
                break;
            case GROUP_NUMBER:
                groupNumber = Integer.parseInt(data);
                break;
            case TOTAL_NUM_OF_TASK:
                totalNumOfTasks = Integer.parseInt(data);
                break;
            case NUM_OF_DONE_TASKS:
                numOfDoneTasks = Integer.parseInt(data);
                break;
            case PROGRAMMING_LANGUAGE:
                programmingLanguage = data;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        data = new String(ch, start, length);
    }
}
