package lt.codeacademy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lt.codeacademy.services.ExamService;
import lt.codeacademy.services.ResultService;

import java.io.*;
import java.time.LocalDate;

public class Main {
    private static final String RESOURCES_PATH = "src/main/resources";

    public static void main(String[] args) throws IOException {
//        String examDir = args[0];
//        String answersFile = args[1];
        Main main = new Main();
        String answersDir = RESOURCES_PATH + "/answers/1";
        File examFile = main.getExamFile("/exams/1.json");
        File resultsFile = main.getResultsFile("/results.json");



        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        ExamService examService = mapper.readValue(examFile, ExamService.class);

        ResultService resultService = new ResultService();
        resultService.saveResults(resultsFile, answersDir, mapper, examService);

        LocalDate localDate = LocalDate.now();




        ///////////////////////////////////////////////////////////////////////////////////


    }

    public File getExamFile(String fileName) throws FileNotFoundException {
        File examFile = new java.io.File(RESOURCES_PATH + fileName);
        if (!examFile.exists()) {
            throw new FileNotFoundException("Exam file not found.");
        }
        return examFile;
    }

    public File getResultsFile(String fileName) throws IOException {
        File resultsFile = new File(RESOURCES_PATH + fileName);
        if (!resultsFile.exists()) {
            resultsFile.createNewFile();
        }
        return resultsFile;
    }

}
