package lt.codeacademy.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.codeacademy.data.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.*;

public class AuthService {
    private final ObjectMapper mapper;
    private final File usersFile;

    public AuthService(ObjectMapper mapper, File usersFile) {
        this.mapper = mapper;
        this.usersFile = usersFile;
    }

    public User menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(" ___________________________________");
            System.out.println("| 1 - Prisijungimas                 |");
            System.out.println("| 2 - Registracija                  |");
            System.out.println("| 3 - Iseiti                        |");
            System.out.println("|___________________________________|");

            String select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    while (true) {
                        try {
                            return login(sc);
                        } catch (AccessDeniedException e) {
                            System.out.println("Neteisingi prisijungimo duomenys.");
                        } catch (Exception e) {
                            System.out.println("Nepavyko prisijungti.");
                        }
                    }
                }
                case "2" -> {
                    registration(sc);
                }
                case "3" -> {
                    return null;
                }
                default -> {
                    System.out.println("Pasirinkite veiksma.");
                }
            }
        }
    }

    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>();
        try {
            usersList = mapper.readValue(usersFile, new TypeReference<>() {
            });
        } catch (Exception ignored) {
        }
        return usersList;
    }

    public void registration(Scanner sc) {
        System.out.println("*** Registracija ***");
        int id = getUsers().size() + 1;
        String userName = getCorrectUserName(sc);
        String password = getCorrectPassword(sc);

        List<User> usersList = getUsers();
        usersList.add(new User(id, userName, password));
        System.out.println("-----------------------------------");
        try {
            mapper.writeValue(usersFile, usersList);
            System.out.println("---");
            System.out.println("Jus sekmingai uzsiregistravote.");
        } catch (IOException e) {
            System.out.println("---");
            System.out.println("Nepavyko irasyti varototjo i duomenu baze.");
        }

    }

    public User login(Scanner sc) throws AccessDeniedException {
        System.out.println("*** Prisijungimas ***");
        System.out.println("Iveskite slapyvardi:");
        String username = sc.nextLine();
        System.out.println("Iveskite slaptazodi:");
        String password = sc.nextLine();

        User user = getUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .get();

        if (user.getPassword() != null && user.getPassword().equals(DigestUtils.sha256Hex(password))) {
            System.out.println("Sveiki, " + user.getUsername() + ".");
            System.out.println(user.getRole() > 0
                    ? "Jus prisijungete kaip mokytojas."
                    : "Jus prisijungete kaip studentas."
            );
            return user;
        } else {
            throw new AccessDeniedException("");
        }
    }

    private String getCorrectPassword(Scanner sc) {
        String password;
        String repeatPassword;
        while (true) {
            System.out.println("Iveskite slaptazodi:");
            password = sc.nextLine();
            try {
                isPasswordValid(password);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
            System.out.println("Pakartokite slaptazodi:");
            repeatPassword = sc.nextLine();
            if (!password.equals(repeatPassword)) {
                System.out.println("---");
                System.out.println("Slaptazodziai nesutampa.");
                continue;
            }
            return DigestUtils.sha256Hex(password);
        }
    }

    private String getCorrectUserName(Scanner sc) {
        String userName;
        while (true) {
            System.out.println("Iveskite slapyvardi:");
            userName = sc.nextLine();
            try {
                isUsernameValid(userName);
                return userName;
            } catch (IllegalArgumentException e) {
                System.out.println("---");
                System.out.println(e.getMessage());
            }
        }
    }

    private void isUsernameValid(final String username) {
        int minLength = 5;
        if (getUsers().stream().anyMatch(o -> o.getUsername().equals(username))) {
            throw new IllegalArgumentException("Toks slapyvardis jau registruotas.");
        }
        if (username.length() < minLength) {
            throw new IllegalArgumentException("Slapyvardi turi sudaryti bent " + minLength + " simboliai.");
        }
    }

    private void isPasswordValid(final String password) {
        int minLength = 8;
        if (password.length() < minLength) {
            throw new IllegalArgumentException("Slaptazodi turi sudaryti bent " + minLength + " simboliai.");
        }
    }
}