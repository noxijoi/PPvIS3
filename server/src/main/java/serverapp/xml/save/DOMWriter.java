package serverapp.xml.save;

import lib.entity.Name;
import lib.entity.StudArray;
import lib.entity.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class DOMWriter {
    private Document document;

    private final String STUD_ARRAY = "stud-array";
    private final String STUD_ARRAY_NAME = "stud-array-name";
    private final String STUDENTS = "students";
    private final String FIO = "name";
    private final String STUDENT = "student";
    private final String FIRST_NAME = "first-name";
    private final String LAST_NAME = "last-name";
    private final String PATRONYMIC = "patronymic";
    private final String COURSE = "course";
    private final String GROUP_NUMBER = "group-number";
    private final String TOTAL_NUM_OF_TASK = "total-num-of-tasks";
    private final String NUM_OF_DONE_TASKS = "num-of-done-tasks";
    private final String PROGRAMMING_LANGUAGE = "programming-language";

    public File writeToFile(String path, StudArray studArray){
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        File result = new File(path);
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();

            Element root = document.createElement(STUD_ARRAY);
            document.appendChild(root);

            Element name = document.createElement(STUD_ARRAY_NAME);
            name.appendChild(document.createTextNode(studArray.getName()));
            root.appendChild(name);

            Element studsEl = document.createElement(STUDENTS);
            root.appendChild(studsEl);



            List<Student> students = studArray.getStudents();
            for (Student student : students) {
                Element studentElement = createStudent(student);
                studsEl.appendChild(studentElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult(result);
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Element createStudent(Student student) {
        Element studentElement = document.createElement(STUDENT);

        Element nameElement = createName
                (student.getName());
        studentElement.appendChild(nameElement);

        Element courseElement = document.createElement(COURSE);
        courseElement.appendChild(document.createTextNode(student.getCourse().toString()));
        studentElement.appendChild(courseElement);

        Element groupElement = document.createElement(GROUP_NUMBER);
        groupElement.appendChild(document.createTextNode(student.getGroupNumber().toString()));
        studentElement.appendChild(groupElement);

        Element totalNumOfTaskElement = document.createElement(TOTAL_NUM_OF_TASK);
        totalNumOfTaskElement.appendChild(document.createTextNode(student.getTotalNumOfTask().toString()));
        studentElement.appendChild(totalNumOfTaskElement);

        Element numOfDoneTaskElement = document.createElement(NUM_OF_DONE_TASKS);
        numOfDoneTaskElement.appendChild(document.createTextNode(student.getNumOfDoneTasks().toString()));
        studentElement.appendChild(numOfDoneTaskElement);

        Element progLangElement = document.createElement(PROGRAMMING_LANGUAGE);
        progLangElement.appendChild(document.createTextNode(student.getProgrammingLanguage()));
        studentElement.appendChild(progLangElement);

        return studentElement;
    }

    private Element createName
            (Name name) {
        Element nameElement = document.createElement(FIO);

        Element fNameElement = document.createElement(FIRST_NAME);
        fNameElement.appendChild(document.createTextNode(name.firstName));
        nameElement.appendChild(fNameElement);

        Element lNameElement = document.createElement(LAST_NAME);
        lNameElement.appendChild(document.createTextNode(name.lastName));
        nameElement.appendChild(lNameElement);

        Element patronymicElement = document.createElement(PATRONYMIC);
        patronymicElement.appendChild(document.createTextNode(name.patronymic));
        nameElement.appendChild(patronymicElement);
        return nameElement;
    }
}
