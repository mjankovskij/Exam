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

    public int validateExam(List<Answer> answersArray) {
        int correctAnswers = 0;

        for (Answer answer : answersArray) {
            if (answers.get(answer.getId() - 1).getAnswer() == answer.getAnswer()) {
                correctAnswers++;
            }
        }

        return (int) (10f / answers.size() * correctAnswers);
    }

}
