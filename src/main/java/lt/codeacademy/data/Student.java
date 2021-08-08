package lt.codeacademy.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.codeacademy.services.DatetimeService;
import lt.codeacademy.services.ExamService;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Student {
    private int id;
    private String firstname;
    private String lastname;
    private int result;
    private String datetime;

    public Student() {
    }

    public Student(int id, String firstname, String lastname, int result, String datetime) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.result = result;
        this.datetime = datetime;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
