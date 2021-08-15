package lt.codeacademy.data;

import lt.codeacademy.services.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamTest {
    private Exam exam;

    @BeforeEach
    void ExamTeat() {
        exam = new Exam(1, "Testinis egzaminas", "testas");

    }

    @Test
    void isExamsSame() {
        Exam examStudent = new Exam(1, "Testinis egzaminas", "testas");
        assertEquals(exam.toString(), examStudent.toString());
    }

    @Test
    void isExamsNotSame1() {
        Exam examStudent = new Exam(1, "Testinis egzaminas", "test");
        assertNotEquals(exam.toString(), examStudent.toString());
    }

    @Test
    void isExamsNotSame2() {
        Exam examStudent = new Exam(1, "Testinis", "testas");
        assertNotEquals(exam.toString(), examStudent.toString());
    }

    @Test
    void isExamsNotSame3() {
        Exam examStudent = new Exam(1, "Testinis", "testas");
        assertNotEquals(exam.toString(), examStudent.toString());
    }

}