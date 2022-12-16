package sk.markot.employeeanalyzer.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileUtilsTest {

    @Test
    void testGetResourceFile_invalidFileName() throws FileNotFoundException {
        String fileName = "invalidFileName.csv";

        assertThrows(FileNotFoundException.class,
                () -> FileUtils.getResourceFile(fileName));
    }

    @Test
    void testGetResourceFile_validFileName() throws FileNotFoundException {
        String fileName = "testEmployees.csv";

        File file = FileUtils.getResourceFile(fileName);

        assertThat(file).isNotEmpty();
    }
}