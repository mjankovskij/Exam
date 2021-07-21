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


    public List<User> getUsers() throws IOException {
        List<User> usersList = new ArrayList<>();
        try {
            usersList = mapper.readValue(usersFile, new TypeReference<>() {
            });
        } catch (Exception ignored) {
            // if there is no users, who cares, maybe it is used for registration.
        }
        return usersList;
    }


    public void registration(Scanner sc) throws IOException {
        System.out.println("*** User Registration ***");
        String userName = getUniqueUserName(sc);
        String password = getCorrectPassword(sc);

        List<User> usersList = getUsers();
        usersList.add(new User(userName, password));
        mapper.writeValue(usersFile, usersList);
        System.out.println("User successfully registered");
    }

    public User login(Scanner sc) throws IOException {
        System.out.println("*** User login ***");
        System.out.println("Insert username");
        String username = sc.nextLine();
        System.out.println("Insert password");
        String password = sc.nextLine();

        User user = getUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .get();

        if (user.getPassword() != null && user.getPassword().equals(DigestUtils.sha256Hex(password))) {
            System.out.println("User login successfully");
            return user;
        } else {
            throw new AccessDeniedException("");
        }
    }

    private String getCorrectPassword(Scanner sc) {
        String password;
        String repeatPassword;
        String text = "";
        do {
            System.out.println(text);
            System.out.println("Please insert password");
            password = sc.nextLine();
            System.out.println("Repeat you password");
            repeatPassword = sc.nextLine();
            text = "Passwords not equals";
        } while (!password.equals(repeatPassword));

        return DigestUtils.sha256Hex(password);
    }

    private String getUniqueUserName(Scanner sc) throws IOException {
        String userName;
        String text = "Please insert username";
        do {
            System.out.println(text);
            userName = sc.nextLine();
            text = "This name exist please insert another one";
        } while (isUserUnique(userName));
        return userName;
    }

    private Boolean isUserUnique(final String username) throws IOException {
        return getUsers().stream().anyMatch(o -> o.getUsername().equals(username));
    }

}