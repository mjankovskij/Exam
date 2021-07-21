package lt.codeacademy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lt.codeacademy.data.User;
import lt.codeacademy.services.AuthService;
import lt.codeacademy.services.ExamService;
import lt.codeacademy.services.MenuService;
import lt.codeacademy.services.ResultService;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static final String RESOURCES_PATH = "src/main/resources";

    public static void main(String[] args) throws IOException {
//        String examDir = args[0];
//        String answersFile = args[1];
        Main main = new Main();


        //////////////////////////////////////////////////

        String answersDir = RESOURCES_PATH + "/answers/1";
        File examFile = main.getFile("/exams/1.json");
        File resultsFile = main.getCreateFile("/results.json");
        File usersFile = main.getCreateFile("/users.json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);



        //////////////////////////////////////////////////
        MenuService menuService = new MenuService(mapper, usersFile);
        menuService.menu();
        //////////////////////////////////////////////////

        ExamService examService = mapper.readValue(examFile, ExamService.class);

        ResultService resultService = new ResultService();
        resultService.saveResults(resultsFile, answersDir, mapper, examService);
    }









    public File getFile(String fileName) throws FileNotFoundException {
        File file = new java.io.File(RESOURCES_PATH + fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found.");
        }
        return file;
    }

    public File getCreateFile(String fileName) throws IOException {
        File file = new File(RESOURCES_PATH + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

}
