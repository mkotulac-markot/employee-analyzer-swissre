package sk.markot.employeeanalyzer.exception;

import java.util.Arrays;

public class InvalidCsvDataException extends RuntimeException {

    public InvalidCsvDataException(String[] data) {
        super("Invalid CSV data: " + Arrays.toString(data));
    }
}
