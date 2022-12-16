package sk.markot.employeeanalyzer.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import sk.markot.employeeanalyzer.utils.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class CsvReader {

    public static List<String[]> readFile(String fileName) {
        try (CSVReader reader = createReader(fileName)) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private static CSVReader createReader(String fileName) throws FileNotFoundException {
        return new CSVReaderBuilder(new FileReader(FileUtils.getResourceFile(fileName)))
                .withSkipLines(1)
                .build();
    }

}
