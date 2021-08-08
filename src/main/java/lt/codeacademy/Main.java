package lt.codeacademy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lt.codeacademy.data.Exam;
import lt.codeacademy.data.User;
import lt.codeacademy.services.*;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        FileService fileService = new FileService();
        File usersFile = fileService.getCreateFile("users.json");

        AuthService authService = new AuthService(mapper, usersFile);
        User user = authService.menu();
        try {
            if (user != null) {
                MenuService menuService = new MenuService(user);
                menuService.menu(mapper, fileService);
            }
        } finally {
            System.out.println("Sėkmės! Lauksime sugrįžtant.");
        }
    }
}
