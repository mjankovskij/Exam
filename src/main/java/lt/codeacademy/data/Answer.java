package lt.codeacademy.data;

public class Answer {
    private int id;
    private char answer;

    public Answer() {
    }

    public Answer(int id, char answer) {
        this.id = id;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public char getAnswer() {
        return answer;
    }
}
