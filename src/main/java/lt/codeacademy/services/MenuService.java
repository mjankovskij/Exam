package lt.codeacademy.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.codeacademy.data.Answer;
import lt.codeacademy.data.Result;
import lt.codeacademy.data.Student;
import lt.codeacademy.data.User;

import javax.management.BadAttributeValueExpException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    private final User user;
    private File examFile;

    public MenuService(User user) {
        this.user = user;
    }

    public void menu(ObjectMapper mapper, FileService fileService) {
        Scanner sc = new Scanner(System.in);
        ResultService resultService = new ResultService();
        System.out.println("------------------------------------");
        ExamService examService = enterExamFile(mapper, sc, fileService);
        if (user.getRole() > 0) {
            teacherMenu(mapper, sc, fileService, resultService, examService);
        } else {
            enterResultFile(mapper, sc, fileService, resultService, examService);
        }
    }

    private void teacherMenu(ObjectMapper mapper, Scanner sc, FileService fileService, ResultService resultService, ExamService examService) {
        while (true) {
            System.out.println(" ___________________________________");
            System.out.println("| 1 - Tikrinti egzaminų katalogą ALL|");
            System.out.println("| 2 - Tikrinti egzamino failą SINGLE|");
            System.out.println("| 3 - Spausdinti visus atsakymus    |");
            System.out.println("| 4 - Pridėti atsakymą              |");
            System.out.println("| 5 - Išeiti                        |");
            System.out.println("|___________________________________|");

            String select = sc.nextLine();

            switch (select) {
                case "1" -> {
                    enterResultsDir(mapper, sc, fileService, resultService, examService);
                }
                case "2" -> {
                    enterResultFile(mapper, sc, fileService, resultService, examService);
                }
                case "3" -> {
                    printAllResults(mapper, resultService, fileService.getCreateFile("resources/results.json"));
                }
                case "4" -> {
                    addAnswer(mapper, sc, examService);
                }
                case "5" -> {
                    return;
                }
                default -> {
                    System.out.println("Pasirinkite veiksmą.");
                }
            }
        }
    }

    private void printAllResults(ObjectMapper mapper, ResultService resultService, File resultsFile) {
        System.out.println("------------------------------------");
        System.out.println("Visi atsakymai");
        List<Result> resultsList = resultService.getResultsList(mapper, resultsFile);
        for (Result result : resultsList) {
            System.out.println("---");
            System.out.println("Egzaminas: " + result.getExam().getName());
            for (Student student : result.getstudentsResults()) {
                System.out.println(student.getFirstname() + " "
                        + student.getLastname() + ", rezultatas: "
                        + student.getResult());
            }
        }
    }

    private void addAnswer(ObjectMapper mapper, Scanner sc, ExamService examService) {
        System.out.println("Egzamino " + examService.getExam().getName() + " atsakymo pridėjimas.");
        int examId = examService.getAnswers().size() + 1;
        char answerChar;
        while (true) {
            System.out.println("Atsakymo nr.: " + examId + ", įveskite atsakymo varaintą(raidę):");
            String select = sc.nextLine();
            if (select.length() > 1) {
                System.out.println("Atsakymą turi sudaryti tik 1 raidė.");
            } else if (Character.isDigit(select.charAt(0))) {
                System.out.println("Atakymas turi būti raidė.");
            } else {
                answerChar = select.charAt(0);
                break;
            }
        }
        try {
            examService.getAnswers().add(new Answer(examId, answerChar));
            mapper.writeValue(examFile, examService);
            System.out.println("Atsakymas pridėtas.");
        } catch (IOException e) {
            System.out.println("Nepavyko pridėti naujo atsakymo.");
        }
    }

    private ExamService enterExamFile(ObjectMapper mapper, Scanner sc, FileService fileService) {
        File examFile;
        String inputExam;
        ExamService examService;
        while (true) {
            System.out.println("--------------------------------");
            System.out.println("Nurdykite egzamino failą:");
            inputExam = sc.nextLine();
            try {
                examFile = fileService.getFile(inputExam);
                examService = mapper.readValue(examFile, ExamService.class);
                this.examFile = examFile;
                break;
            } catch (IOException e) {
                System.out.println("---");
                System.out.println("Egzaminas nerastas.");
            }
        }
        System.out.println("--------------------------------");
        System.out.println("Pasirinktas egzaminas: " + examService.getExam().getName());
        return examService;
    }

    private void enterResultFile(ObjectMapper mapper, Scanner sc, FileService fileService, ResultService resultService, ExamService examService) {
        File yourFile;
        String inputYour;
        while (true) {
            System.out.println("--------------------------------");
            System.out.println("Nurdykite sprendimo failą:");
            inputYour = sc.nextLine();
            try {
                yourFile = fileService.getFile(inputYour);
                StudentService studentService = mapper.readValue(yourFile, StudentService.class);
                if (!examService.getExam().toString().equals(studentService.getExam().toString())) {
                    throw new BadAttributeValueExpException("");
                }
                resultService.validateResult(mapper, fileService, examService, studentService);
                break;
            } catch (FileNotFoundException e) {
                System.out.println("---");
                System.out.println("Sprendimų failas nerastas.");
            } catch (Exception e) {
                System.out.println("---");
                System.out.println("Sprendimų failas netinkamo formato.");
            }
        }
    }

    private void enterResultsDir(ObjectMapper mapper, Scanner sc, FileService fileService, ResultService resultService, ExamService examService) {
        String inputYour;
        while (true) {
            System.out.println("--------------------------------");
            System.out.println("Nurdykite sprendimų direktoriją:");
            inputYour = sc.nextLine();

            try {
                resultService.validateAllResults(mapper, fileService, inputYour, examService);
                break;
            } catch (Exception e) {
                System.out.println("---");
                System.out.println("Sprendimų dirketorija nerasta.");
            }
        }
    }
}
