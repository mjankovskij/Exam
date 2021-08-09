package lt.codeacademy.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.codeacademy.data.Result;
import lt.codeacademy.data.Student;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ResultService {

    public void validateAllResults(ObjectMapper mapper, FileService fileService, String answersDir, ExamService examService) throws IOException {
        for (String answerFileName : Objects.requireNonNull(new File(answersDir).list())) {
            File answerFile = new File(answersDir + "/" + answerFileName);
            StudentService studentService = mapper.readValue(answerFile, StudentService.class);

            validateResult(mapper, fileService, examService, studentService);
        }
    }

    public void validateResult(ObjectMapper mapper, FileService fileService, ExamService examService, StudentService studentService) {
        File resultsFile = fileService.getCreateFile("resources/results.json");
        String firstname = studentService.getStudent().getFirstname();
        String lastname = studentService.getStudent().getLastname();

        List<Result> resultsList = getResultsList(mapper, resultsFile);
        Result result = new Result();
        try {
            result = resultsList.stream()
                    .filter(r -> r.getExam().getId() == examService.getExam().getId())
                    .findFirst()
                    .get();
        } catch (Exception e) {
            resultsList.add(result);
        }

        List<Student> studentList = result.getstudentsResults();
        Student student = studentService.getStudent();
        try {
            student = studentList.stream()
                    .filter(r -> r.getId() == studentService.getStudent().getId())
                    .findFirst()
                    .get();
        } catch (Exception e) {
            if (studentList == null) {
                studentList = new ArrayList<>();
            }
            studentList.add(student);
        }

        if (student.getDatetime() == null || DatetimeService.hasTimePassed(student.getDatetime())) {
            int resultInt = examService.validateExam(studentService.getAnswers());

            studentService.getStudent().setResult(resultInt);
            result.setStudentsResults(studentList);
            result.setExam(examService.getExam());
            student.setDatetime(DatetimeService.getDateTimeString());

            System.out.println(firstname + " "
                    + lastname
                    + ", įvertinimas: "
                    + resultInt
            );
            saveResult(mapper, resultsFile, resultsList);
        } else {
            System.out.println(firstname + " "
                    + lastname
                    + ", neparaėjo 2h."
            );
        }
    }

    public List<Result> getResultsList(ObjectMapper mapper, File resultsFile) {
        List<Result> resultsList = new ArrayList<>();
        try {
            resultsList = mapper.readValue(resultsFile, new TypeReference<>() {
            });
        } catch (Exception ignored) {
        }
        return resultsList;
    }

    private void saveResult(ObjectMapper mapper, File resultsFile, List<Result> resultsList) {
        try {
            mapper.writeValue(resultsFile, resultsList);
        } catch (IOException e) {
            System.out.println("---");
            System.out.println("Nepavyko įrašyti rezultatų.");
        }
    }

}
