package sk.markot.employeeanalyzer;

import sk.markot.employeeanalyzer.analyzer.Analyzer;
import sk.markot.employeeanalyzer.csv.CsvReader;
import sk.markot.employeeanalyzer.config.PropertiesLoader;
import sk.markot.employeeanalyzer.data.Employee;
import sk.markot.employeeanalyzer.data.EmployeeStructure;
import sk.markot.employeeanalyzer.data.mapper.EmployeeDataMapper;

import java.util.List;
import java.util.Properties;

public class EmployeeAnalyzerApp {
    public static void main(String[] args) {
        List<String[]> fileContent = CsvReader.readFile("employees.csv");
        Properties properties = PropertiesLoader.loadProperties("config.properties");

        List<Employee> allEmployees = EmployeeDataMapper.csvDataToEmployees(fileContent);
        EmployeeStructure employeeStructure = new EmployeeStructure(allEmployees);

        Analyzer analyzer = new Analyzer(properties);
        analyzer.analyzeReportingLine(employeeStructure.getStructure());
        analyzer.analyzeManagersSalary(employeeStructure.getStructure());
    }
}