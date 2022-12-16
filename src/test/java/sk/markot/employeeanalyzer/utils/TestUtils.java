package sk.markot.employeeanalyzer.utils;

import sk.markot.employeeanalyzer.data.Employee;
import sk.markot.employeeanalyzer.data.EmployeeStructure;
import sk.markot.employeeanalyzer.data.Node;

import java.util.List;
import java.util.Properties;

public class TestUtils {

    public static Properties createProperties(String... params) {
        Properties properties = new Properties();
        properties.setProperty("manager.salary-surcharge.lower-limit", params[0]);
        properties.setProperty("manager.salary-surcharge.upper-limit", params[1]);
        properties.setProperty("employee.managers.count.max", params[2]);
        return properties;
    }

    public static Node<Employee> createEmployeeStructure(Employee... employees) {
        return new EmployeeStructure(List.of(employees)).getStructure();
    }

    public static Node<Employee> createEmployeeStructure() {
        List<Employee> employees = List.of(
                createEmployee("1", "a", "b", "2000", ""),
                createEmployee("2", "a", "b", "2000", "1"),
                createEmployee("3", "a", "b", "500", "2"),
                createEmployee("4", "a", "b", "500", "3"),
                createEmployee("5", "a", "b", "500", "4"));

        return new EmployeeStructure(employees).getStructure();
    }

    public static Employee createEmployee(String... params) {
        return new Employee(params);
    }


    public static String[] createInvalidEmployeeData() {
        return new String[0];
    }

    public static String[] createValidEmployeeData() {
        return new String[]{"1", "a", "b", "1", "0"};
    }
}
