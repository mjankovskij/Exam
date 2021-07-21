package lt.codeacademy.data;

import lt.codeacademy.services.ExamService;

public class Student {
    private int id;
    private String firstname;
    private String lastname;
    private int result;

    public Student() {
        super();
    }

    public Student(int id, String firstname, String lastname, int result) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getResult() {
        return result;
    }

}
