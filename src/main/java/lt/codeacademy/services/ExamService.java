package lt.codeacademy.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.codeacademy.data.Answer;
import lt.codeacademy.data.Exam;
import lt.codeacademy.data.Student;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExamService {
    private Exam exam;
    private List<Answer> answers;

    public ExamService() {
    }

    public ExamService(Exam exam, List<Answer> answers) {
        this.exam = exam;
        this.answers = answers;
    }

    public Exam getExam() {
        return exam;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public int validateExam(List<Answer> studentArray) {
        int correctAnswers = 0;

        for (Answer studentAnswer : studentArray) {
            if (answers.get(studentAnswer.getId() - 1).getAnswer() == studentAnswer.getAnswer()) {
                correctAnswers++;
            }
        }

        return (int) (10f / answers.size() * correctAnswers);
    }

    public List<Student> validateAll(ObjectMapper mapper, String answersDir) throws IOException {
        List<Student> studentList = new ArrayList<>();
        for (String answerFileName : Objects.requireNonNull(new File(answersDir).list())) {
            File answerFile = new File(answersDir + "/" + answerFileName);

            StudentService studentService = mapper.readValue(answerFile, StudentService.class);

            int res = validateExam(studentService.getAnswers());
            studentService.getStudent().setResult(res);

            studentList.add(studentService.getStudent());

            System.out.println("Student " + studentService.getStudent().getFirstname() + ", grade: " + res);
        }
        return studentList;
    }
}
