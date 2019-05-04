package clientapp.entity;

public class Student {
    private Name name;
    private Integer course;
    private Integer groupNumber;
    private Integer totalNumOfTask;
    private Integer numOfDoneTasks;
    private String programmingLanguage;

    public Student(Name name, int course, int groupNumber,
                   int totalNumOfTask, int numOfDoneTasks, String programmingLanguage) {
        this.name = name;
        this.course = course;
        this.groupNumber = groupNumber;
        this.totalNumOfTask = totalNumOfTask;
        this.numOfDoneTasks = numOfDoneTasks;
        this.programmingLanguage = programmingLanguage;
    }

    public Student() {
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Integer getTotalNumOfTask() {
        return totalNumOfTask;
    }

    public void setTotalNumOfTask(Integer totalNumOfTask) {
        this.totalNumOfTask = totalNumOfTask;
    }

    public Integer getNumOfDoneTasks() {
        return numOfDoneTasks;
    }

    public void setNumOfDoneTasks(Integer numOfDoneTasks) {
        this.numOfDoneTasks = numOfDoneTasks;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }
}
