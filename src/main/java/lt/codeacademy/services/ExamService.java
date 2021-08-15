package lt.codeacademy.services;

import lt.codeacademy.data.Answer;
import lt.codeacademy.data.Exam;

import javax.crypto.IllegalBlockSizeException;
import java.util.List;

public class ExamService {
    private Exam exam;
    private List<Answer<Integer, String>> answers;

    public ExamService() {
    }

    public ExamService(Exam exam, List<Answer<Integer, String>> answers) {
        this.exam = exam;
        this.answers = answers;
    }

    public Exam getExam() {
        return exam;
    }

    public List<Answer<Integer, String>> getAnswers() {
        return answers;
    }

    public int validateExam(List<Answer<Integer, String>> answersArray) throws IllegalBlockSizeException {
        if(answersArray.size()!=answers.size()){
           throw new IllegalBlockSizeException("Studento atsakymų kiekis neatitinka egzamino.");
        }
        int correctAnswers = 0;

        for (Answer<Integer, String> answer : answersArray) {
            if (answers.get(answer.getKey() - 1).getValue().equals(answer.getValue())) {
                correctAnswers++;
            }
        }

        return (int) (10f / answers.size() * correctAnswers);
    }

}
