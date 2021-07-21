package lt.codeacademy.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.codeacademy.data.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Scanner;

public class MenuService {
    private final AuthService authService;

    public MenuService(ObjectMapper mapper, File usersFile) {
        this.authService = new AuthService(mapper, usersFile);
    }

    public void menu() throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean isLoading = true;
        while (isLoading) {
            System.out.println(" ___________________________________");
            System.out.println("| 1 - User registration             |");
            System.out.println("| 2 - User login                    |");
            System.out.println("| 3 - Exit                          |");
            System.out.println("|___________________________________|");

            String select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    authService.registration(sc);
                }
                case "2" -> {
                    while(true){
                        try {
                            menuAuthorized(authService.login(sc));
                            return;
                        }
                        catch(Exception e){
                            System.out.println("Login error, please check credentials or create account");
                        }
                    }

                }
                case "3" -> {
                    isLoading = false;
                }
                default -> {
                    System.out.println("Please select menu item");
                }
            }
        }
    }

    public void menuAuthorized(User user) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean isLoading = true;
        while(isLoading) {
            if(user.getRole()>0){
                System.out.println("OPANKI");
            }
            System.out.println(" ___________________________________");
            System.out.println("| 1 - Validate exams                |");
            System.out.println("| 2 - Add exam fields               |");
            System.out.println("| 3 - Exit                          |");
            System.out.println("|___________________________________|");

            String select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    authService.registration(sc);
                }
                case "2" -> {
                    authService.login(sc);
                }
                case "3" -> {
                    isLoading = false;
                }
                default -> {
                    System.out.println("Please select menu item");
                }
            }
        }
    }
}
