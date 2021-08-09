package lt.codeacademy.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileService {

    public File getFile(String fileName) throws FileNotFoundException {
        File file = new java.io.File(fileName);
        if (!file.exists()) {
            System.out.println("---");
            throw new FileNotFoundException("Failas nerastas.");
        }
        return file;
    }

    public File getCreateFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        return file;
    }

}
