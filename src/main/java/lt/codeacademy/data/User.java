package lt.codeacademy.data;

public class User {
    private String username;
    private String password;
    private int role;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }
}
