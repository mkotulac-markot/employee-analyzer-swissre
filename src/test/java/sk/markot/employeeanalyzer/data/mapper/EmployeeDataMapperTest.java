package sk.markot.employeeanalyzer.data.mapper;

import org.junit.jupiter.api.Test;
import sk.markot.employeeanalyzer.data.Employee;
import sk.markot.employeeanalyzer.exception.InvalidCsvDataException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static sk.markot.employeeanalyzer.utils.TestUtils.createInvalidEmployeeData;
import static sk.markot.employeeanalyzer.utils.TestUtils.createValidEmployeeData;

class EmployeeDataMapperTest {

    @Test
    void mapToEmployee_invalidDataThrowsException() {
        String[] data = createInvalidEmployeeData();

        assertThrows(InvalidCsvDataException.class,
                () -> EmployeeDataMapper.mapToEmployee(data));
    }

    @Test
    void mapToEmployee_validData() {
        String[] data = createValidEmployeeData();

        Employee employee = EmployeeDataMapper.mapToEmployee(data);

        assertThat(employee.getId()).isEqualTo("1");
        assertThat(employee.getFirstName()).isEqualTo("a");
        assertThat(employee.getLastName()).isEqualTo("b");
        assertThat(employee.getSalary()).isEqualTo("1");
        assertThat(employee.getManagerId()).isEqualTo("0");
    }

    @Test
    void csvDataToEmployees() {
        List<String[]> data = Collections.singletonList(createValidEmployeeData());

        List<Employee> employees = EmployeeDataMapper.csvDataToEmployees(data);

        assertThat(employees).hasSize(1);
        assertThat(employees.get(0).getId()).isEqualTo("1");
        assertThat(employees.get(0).getFirstName()).isEqualTo("a");
        assertThat(employees.get(0).getLastName()).isEqualTo("b");
        assertThat(employees.get(0).getSalary()).isEqualTo("1");
        assertThat(employees.get(0).getManagerId()).isEqualTo("0");
    }
}