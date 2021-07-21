package lt.codeacademy.data;

import java.util.List;

public class Result {
    private Exam exam;
    private List<Student> studentsResults;

    public Result() {
    }

    public Result(Exam exam, List<Student> studentsResults) {
        this.exam = exam;
        this.studentsResults = studentsResults;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<Student> getstudentsResults() {
        return studentsResults;
    }

    public void setStudentsResults(List<Student> studentsResults) {
        this.studentsResults = studentsResults;
    }
}
