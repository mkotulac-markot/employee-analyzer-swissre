package sk.markot.employeeanalyzer.analyzer;

import org.junit.jupiter.api.Test;
import sk.markot.employeeanalyzer.data.Employee;
import sk.markot.employeeanalyzer.data.Node;

import java.math.BigDecimal;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static sk.markot.employeeanalyzer.analyzer.AnalyzeResultType.*;
import static sk.markot.employeeanalyzer.utils.TestUtils.createEmployeeStructure;
import static sk.markot.employeeanalyzer.utils.TestUtils.createProperties;

class AnalyzerTest {

    @Test
    void testAnalyzeReportingLine_exceeded() {
        Node<Employee> employeeStructure = createEmployeeStructure();
        Properties properties = createProperties("20", "50", "2");

        Analyzer analyzer = new Analyzer(properties);
        AnalyzeResult result = analyzer.analyzeReportingLine(employeeStructure);

        assertThat(result.getResults(REPORTING_LINE))
                .hasSize(1)
                .containsExactly("5");
    }

    @Test
    void testAnalyzeReportingLine_notExceeded() {
        Node<Employee> employeeStructure = createEmployeeStructure();
        Properties properties = createProperties("20", "50", "10");

        Analyzer analyzer = new Analyzer(properties);
        AnalyzeResult result = analyzer.analyzeReportingLine(employeeStructure);

        assertThat(result.getResults(REPORTING_LINE)).isEmpty();
    }

    @Test
    void analyzeManagersSalary_lessThanSubiderectsAverage() {
        Node<Employee> employeeStructure = createEmployeeStructure(
                new Employee("1", "a", "b", BigDecimal.valueOf(1000), ""),
                new Employee("2", "a", "b", BigDecimal.valueOf(1000), "1"));
        Properties properties = createProperties("20", "50", "10");

        Analyzer analyzer = new Analyzer(properties);
        AnalyzeResult result = analyzer.analyzeManagersSalary(employeeStructure);

        assertThat(result.getResults(MIN_SALARY))
                .hasSize(1)
                .containsExactly("1");
    }

    @Test
    void analyzeManagersSalary_moreThanSubiderectsAverage() {
        Node<Employee> employeeStructure = createEmployeeStructure(
                new Employee("1", "a", "b", BigDecimal.valueOf(2000), ""),
                new Employee("2", "a", "b", BigDecimal.valueOf(1000), "1"));
        Properties properties = createProperties("20", "50", "10");

        Analyzer analyzer = new Analyzer(properties);
        AnalyzeResult result = analyzer.analyzeManagersSalary(employeeStructure);

        assertThat(result.getResults(MAX_SALARY))
                .hasSize(1)
                .containsExactly("1");
    }
}