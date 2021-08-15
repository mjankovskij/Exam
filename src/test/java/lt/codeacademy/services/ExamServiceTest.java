package lt.codeacademy.services;

import lt.codeacademy.data.Answer;
import lt.codeacademy.data.Exam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.IllegalBlockSizeException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamServiceTest {
    private ExamService examService;

    @BeforeEach
    void beforeEach() {
        Exam exam = new Exam(1, "Testinis egzaminas", "testas");
        List<Answer<Integer, String>> answersExam = new ArrayList<>();
        answersExam.add(new Answer<>(1, "a"));
        answersExam.add(new Answer<>(2, "b"));
        answersExam.add(new Answer<>(3, "c"));
        answersExam.add(new Answer<>(4, "d"));
        examService = new ExamService(exam, answersExam);
    }

    @Test
    void validateExamTen() throws IllegalBlockSizeException {
        List<Answer<Integer, String>> answersStudent = new ArrayList<>();
        answersStudent.add(new Answer<>(1, "a"));
        answersStudent.add(new Answer<>(2, "b"));
        answersStudent.add(new Answer<>(3, "c"));
        answersStudent.add(new Answer<>(4, "d"));

        assertEquals(10, examService.validateExam(answersStudent));
    }

    @Test
    void validateExamSeven() throws IllegalBlockSizeException {
        List<Answer<Integer, String>> answersStudent = new ArrayList<>();
        answersStudent.add(new Answer<>(1, "b"));
        answersStudent.add(new Answer<>(2, "b"));
        answersStudent.add(new Answer<>(3, "c"));
        answersStudent.add(new Answer<>(4, "d"));

        assertEquals(7, examService.validateExam(answersStudent));
    }

    @Test
    void validateExamTooMuchAnswers() {
        List<Answer<Integer, String>> answersStudent = new ArrayList<>();
        answersStudent.add(new Answer<>(1, "a"));
        answersStudent.add(new Answer<>(2, "b"));
        answersStudent.add(new Answer<>(3, "c"));
        answersStudent.add(new Answer<>(4, "d"));
        answersStudent.add(new Answer<>(5, "e"));

        assertThrows(IllegalBlockSizeException.class,  () -> {examService.validateExam(answersStudent);});
    }

    @Test
    void validateExamFewerAnswers() {
        List<Answer<Integer, String>> answersStudent = new ArrayList<>();
        answersStudent.add(new Answer<>(1, "a"));
        answersStudent.add(new Answer<>(2, "b"));

        assertThrows(IllegalBlockSizeException.class,  () -> {examService.validateExam(answersStudent);});
    }

}