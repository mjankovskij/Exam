package lt.codeacademy.services;

import lt.codeacademy.data.Answer;
import lt.codeacademy.data.Exam;
import lt.codeacademy.data.Student;

import java.util.List;

public class StudentService extends ExamService {
    private Student student;

    public StudentService() {
    }

    public StudentService(Student student, Exam exam, List<Answer<Integer, String>> answers) {
        super(exam, answers);
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

}
