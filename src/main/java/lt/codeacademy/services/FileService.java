package lt.codeacademy.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileService {
        private static final String RESOURCES_PATH = "resources/";
//    private static final String RESOURCES_PATH = "src/main/resources/";

    public File getFile(String fileName) throws FileNotFoundException {
        File file = new java.io.File(RESOURCES_PATH + fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found.");
        }
        return file;
    }

    public File getCreateFile(String fileName) {
        File file = new File(RESOURCES_PATH + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        return file;
    }

    public String getAnswersDir(String dir) {
        return RESOURCES_PATH + "/answers/" + dir;
    }
}
