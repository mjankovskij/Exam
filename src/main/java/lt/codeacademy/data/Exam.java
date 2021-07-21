package lt.codeacademy.data;

public class Exam {
    private int id;
    private String name;
    private String type;

    public Exam() {
    }

    public Exam(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
}
