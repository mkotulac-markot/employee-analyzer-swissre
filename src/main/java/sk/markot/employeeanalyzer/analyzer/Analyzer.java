package sk.markot.employeeanalyzer.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.markot.employeeanalyzer.config.Settings;
import sk.markot.employeeanalyzer.data.Employee;
import sk.markot.employeeanalyzer.data.Node;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static sk.markot.employeeanalyzer.analyzer.AnalyzeResultType.*;

public class Analyzer {

    private static final Logger logger = LoggerFactory.getLogger(Analyzer.class);

    private final Settings settings;

    public Analyzer(Properties properties) {
        this.settings = new Settings(properties);
    }

    public AnalyzeResult analyzeReportingLine(Node<Employee> employeesStructure) {
        Map<Employee, Integer> reportingLinesMap = analyzeReportingLine(employeesStructure, null, new HashMap<>());

        AnalyzeResult result = new AnalyzeResult();
        for (Map.Entry<Employee, Integer> entry : reportingLinesMap.entrySet()) {
            Employee employee = entry.getKey();
            Integer currentEmployeeManagersInRow = entry.getValue() - 1;
            Integer maxEmployeeManagersInRow = settings.getMaxEmployeeManagersInRow();

            if (currentEmployeeManagersInRow > maxEmployeeManagersInRow) {
                result.addEmployee(REPORTING_LINE, employee.getId());
                logger.warn("Employee id {}, {} {} has too long reporting line. Exceeded by {}",
                        employee.getId(), employee.getFirstName(), employee.getLastName(),
                        currentEmployeeManagersInRow - maxEmployeeManagersInRow);
            }
        }

        return result;
    }

    public AnalyzeResult analyzeManagersSalary(Node<Employee> employeesStructure) {
        AnalyzeResult result = new AnalyzeResult();
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        Map<Employee, BigDecimal> managersSubdirectsAverageSalary = analyzeManagersSalary(employeesStructure, new HashMap<>());

        for (Map.Entry<Employee, BigDecimal> entry : managersSubdirectsAverageSalary.entrySet()) {
            Employee employee = entry.getKey();
            BigDecimal salary = employee.getSalary();
            BigDecimal minSalary = calculateMinSalary(entry.getValue());
            BigDecimal maxSalary = calculateMaxSalary(entry.getValue());

            if (salary.compareTo(minSalary) < 1) {
                result.addEmployee(MIN_SALARY, employee.getId());
                logger.warn("Manager id {}, {} {} should earn more than {}. By {}",
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        currency.format(minSalary),
                        currency.format(minSalary.subtract(salary)));
            } else if (salary.compareTo(maxSalary) > 0) {
                result.addEmployee(MAX_SALARY, employee.getId());
                logger.warn("Manager id {}, {} {} should earn less than {}. By {}",
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        currency.format(maxSalary),
                        currency.format(salary.subtract(maxSalary)));
            }
        }

        return result;
    }

    private BigDecimal calculateMaxSalary(BigDecimal averageSalary) {
        BigDecimal maxSurcharge = percentage(averageSalary, settings.getManagerSalarySurchargeUpperLimit());
        return averageSalary.add(maxSurcharge);
    }

    private BigDecimal calculateMinSalary(BigDecimal averageSalary) {
        BigDecimal minSurcharge = percentage(averageSalary, settings.getManagerSalarySurchargeLowerLimit());
        return averageSalary.add(minSurcharge);
    }

    private BigDecimal percentage(BigDecimal base, Integer pct) {
        return base.multiply(BigDecimal.valueOf(pct)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
    }

    private Map<Employee, BigDecimal> analyzeManagersSalary(Node<Employee> employeesStructure, Map<Employee, BigDecimal> managerSubdirectsAverageSalary) {
        if (!employeesStructure.isLeaf()) {
            BigDecimal subdirectsSalarySum = employeesStructure.getChildren()
                    .stream()
                    .map(Node::getData)
                    .map(Employee::getSalary)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal employeesCount = BigDecimal.valueOf(employeesStructure.getChildren().size());
            BigDecimal averageSalary = subdirectsSalarySum.divide(employeesCount, RoundingMode.HALF_UP);

            managerSubdirectsAverageSalary.put(employeesStructure.getData(), averageSalary);
        }

        for (Node<Employee> child : employeesStructure.getChildren()) {
            analyzeManagersSalary(child);
        }

        return managerSubdirectsAverageSalary;
    }

    private Map<Employee, Integer> analyzeReportingLine(Node<Employee> structure, Integer level, HashMap<Employee, Integer> reportingLine) {
        Node<Employee> parent = structure.getParent();
        if (parent == null || level == null) {
            level = 0;
        } else {
            level = level + 1;
        }

        reportingLine.put(structure.getData(), level);

        for (Node<Employee> child : structure.getChildren()) {
            analyzeReportingLine(child, level, reportingLine);
        }

        return reportingLine;
    }
}
