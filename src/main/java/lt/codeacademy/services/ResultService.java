package lt.codeacademy.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.codeacademy.data.Result;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultService {

    public void saveResults(File resultsFile, String answersDir, ObjectMapper mapper, ExamService examService) throws IOException {
        List<Result> resultsList = new ArrayList<>();
        Result result = new Result();
        try {
            resultsList = mapper.readValue(resultsFile, new TypeReference<>() {
            });
            result = resultsList.stream()
                    .filter(r -> r.getExam().getId() == examService.getExam().getId())
                    .findFirst()
                    .get();
        } catch (Exception e) {
            if (result.getExam() == null) {
                result.setStudentsResults(examService.validateAll(mapper, answersDir));
                result.setExam(examService.getExam());
                resultsList.add(result);
                mapper.writeValue(resultsFile, resultsList);
            }else{
                System.out.println("Exam is already validated.");
                return;
            }
        }

        System.out.println("Results succesfully saved.");
    }

}
