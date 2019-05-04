package serverapp.managedb;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverapp.entity.Content;
import serverapp.entity.StudArray;
import serverapp.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContentController {
    private final static Logger LOGGER = LogManager.getLogger(ContentController.class);
    private Content content;


    public ContentController() {
        this.content = new Content();
    }

    //? добавление студентов поавильнее делать сдесь или непосредственно в контенте
    public void addStudent(Student student, String tableName) {
        StudArray studArray  = content.getStudArrayByName(tableName);
        studArray.addStudent(student);
    }

    public void addAllStudents(List<Student> studentList, String tableName) {
        StudArray studArray = content.getStudArrayByName(tableName);
        studArray.addAllStudents(studentList);
    }

    public List<Student> delStudentsByParam(Object firstParam, Object secondParam, TypeOfSelection type, String tableName) {
        List<Student> students = content.getStudArrayByName(tableName).getStudents();
        List<Student> removedStudents = findStudentByParam(firstParam, secondParam, type, tableName);
        for (Student removedStudent : removedStudents) {
            students.remove(removedStudent);
        }
        return removedStudents;
    }

    public List<Student> findStudentByParam(Object firstParam, Object secondParam, TypeOfSelection type, String tableName) {
        List<Student> students = content.getStudArrayByName(tableName).getStudents();
        List<Student> foundStudents = new ArrayList<>();
        switch (type) {
            case FIO_OR_GROUP:
                foundStudents = students.stream()
                        .filter(stud -> stud.getName().equals(firstParam) || stud.getGroupNumber().equals(secondParam))
                        .collect(Collectors.toList());
                break;
            case NUM_OF_TASKS:
                foundStudents = students.stream()
                        .filter(stud -> stud.getTotalNumOfTask().equals(firstParam) || stud.getNumOfDoneTasks().equals(secondParam))
                        .collect(Collectors.toList());
                break;
            case COURSE_OR_PL:
                foundStudents = students.stream()
                        .filter(stud -> stud.getCourse().equals(firstParam) || stud.getProgrammingLanguage().equals(secondParam))
                        .collect(Collectors.toList());
                break;
            case NUM_OF_UNDONE_TASKS:
                foundStudents = students.stream()
                        .filter(stud -> {
                            Integer numUndoneTasks = (stud.getTotalNumOfTask()- stud.getNumOfDoneTasks());
                            return numUndoneTasks.equals(firstParam);
                        })
                        .collect(Collectors.toList());
                break;
        }
        return foundStudents;
    }

    public List<String> getAllProgrammingLanguages(String tableName) {
        StudArray studArray = content.getStudArrayByName(tableName);
        List<String> progLangs = studArray.getStudents().stream()
                .flatMap(stud -> Stream.of(stud.getProgrammingLanguage()))
                .distinct()
                .collect(Collectors.toList());
        return progLangs;
    }

    public List<String> getAllTablesNames() {

        List<String> tableNames = new ArrayList<>();
        List<StudArray> studArrays = content.getStudArrays();

        studArrays.forEach(studArray -> tableNames.add(studArray.getName()));

        return tableNames;
    }

    private String replaceTableName(String query, String tableName) {
        return  query.replace("$tableName", tableName);
    }



    public boolean dropStudArray(String name) {
        StudArray studArray =content.getStudArrayByName(name);
        if(studArray == null){
            return false;
        }
        content.remove(studArray);
        return true;
    }

    public boolean createStudentsTable(String tableName) {
        return content.createNewArr(tableName);
    }

    public List<Student> getPage(int pageSize, int pageNum, String tableName) {
        List<Student> resultPage = new ArrayList<>();
        List<Student> students = content.getStudArrayByName(tableName).getStudents();
        int pages = students.size() / pageSize;
        if(pageNum < pages){
            resultPage = students.subList(pageNum * pageSize,
                    pageNum * pageSize + pageSize);
        } else if(pageNum == pages){
            resultPage = students.subList(pageNum * pageSize, students.size());
        }
        return resultPage;
    }

    public int getRecordsNum(String tableName) {
        return content.getStudArrayByName(tableName).size();

    }

    public int getPagesNum(int recordsPerPage, String tableName) {
        int recordsNum = getRecordsNum(tableName);
        if(recordsNum % recordsPerPage == 0){
            return recordsNum / recordsPerPage;
        } else {
            return recordsNum / recordsPerPage + 1;
        }
    }


    public void addStudArray(StudArray studArray) {
        content.addStudArray(studArray);
    }

    public void clearTable(String tableName) {
        StudArray studArray = content.getStudArrayByName(tableName);
        studArray.clear();

    }

    public StudArray getStudArray(String currentTableName) {
        return content.getStudArrayByName(currentTableName);
    }
}

