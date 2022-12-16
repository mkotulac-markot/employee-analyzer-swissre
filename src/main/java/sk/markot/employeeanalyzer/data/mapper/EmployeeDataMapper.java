package sk.markot.employeeanalyzer.data.mapper;

import sk.markot.employeeanalyzer.data.Employee;
import sk.markot.employeeanalyzer.exception.InvalidCsvDataException;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDataMapper {

    public static Employee mapToEmployee(String[] data) {
        if (data.length != 5) {
            throw new InvalidCsvDataException(data);
        }
        return new Employee(data);
    }

    public static List<Employee> csvDataToEmployees(List<String[]> data) {
        return data.stream()
                .map(EmployeeDataMapper::mapToEmployee)
                .collect(Collectors.toList());
    }
}
