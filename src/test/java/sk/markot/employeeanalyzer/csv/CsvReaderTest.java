package sk.markot.employeeanalyzer.csv;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvReaderTest {

    @Test
    void readFile_invalidFileName() {
        String fileName = "invalidFileName.csv";

        assertThrows(RuntimeException.class,
                () -> CsvReader.readFile(fileName));
    }

    @Test
    void readFile_validFileName() {
        String fileName = "testEmployees.csv";

        List<String[]> fileContent = CsvReader.readFile(fileName);

        assertThat(fileContent).hasSize(3);
    }
}