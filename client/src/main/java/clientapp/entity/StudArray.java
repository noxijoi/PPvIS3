package clientapp.entity;

import java.util.ArrayList;
import java.util.List;

public class StudArray {
    private String name;
    private List<Student> students;

    public StudArray(String name) {
        this.name = name;
    }

    public StudArray() {

    }

    public List<Student> getStudents() {
        return students;
    }

    public void clear() {
        students.clear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setStudents(List<Student> studentList) {
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addAllStudents(List<Student> studentList) {
        students.addAll(studentList);
    }

    public int size() {
        return students.size();
    }
}
