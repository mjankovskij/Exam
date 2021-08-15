package lt.codeacademy.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {
    private FileService fileService;

    @BeforeEach
    void beforeEach() {
        fileService = new FileService();
    }

    @Test
    void getCreateUsers() {
        assertDoesNotThrow(() -> {fileService.getCreateFile("src/main/resources/users.json");});
    }

    @Test
    void getCreateResults() {
        assertDoesNotThrow(() -> {fileService.getCreateFile("src/main/resources/results.json");});
    }

}