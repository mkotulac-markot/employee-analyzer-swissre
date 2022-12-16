package sk.markot.employeeanalyzer.data;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.markot.employeeanalyzer.exception.EmployeeException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeStructure {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeStructure.class);

    private final Node<Employee> structure;

    public EmployeeStructure(List<Employee> employees) {
        this.structure = createStructure(employees);
    }

    private Node<Employee> createStructure(List<Employee> allEmployees) {
        Map<String, List<Employee>> employeesByManager = allEmployees.stream()
                .collect(Collectors.groupingBy(Employee::getManagerId));

        Node<Employee> root = new Node<>(findCeo(employeesByManager));
        logger.info("Create structure, root ID: {}", root.getData().getId());

        Map<String, Node<Employee>> nodes = new HashMap<>();
        for (Employee employee : allEmployees) {

            Node<Employee> employeeNode = getNode(nodes, employee);
            for (Employee subordinate : employeesByManager.getOrDefault(employee.getId(), Collections.emptyList())) {
                Node<Employee> subordinateNode = getNode(nodes, subordinate);
                employeeNode.addChild(subordinateNode);
            }

            if (root.getData().getId().equals(employeeNode.getData().getManagerId())) {
                root.addChild(employeeNode);
            }
        }

        return root;
    }

    public Node<Employee> getStructure() {
        return structure;
    }

    private Node<Employee> getNode(Map<String, Node<Employee>> nodes, Employee employee) {
        return nodes.computeIfAbsent(employee.getId(), k -> new Node<>(employee));
    }

    private Employee findCeo(Map<String, List<Employee>> employeesByManager) {
        List<Employee> employeesWithoutManger = employeesByManager.get("");
        if (CollectionUtils.isEmpty(employeesWithoutManger)) {
            throw new EmployeeException("No CEO found");
        } else if (employeesWithoutManger.size() > 1) {
            throw new EmployeeException("More than 1 CEO found");
        } else {
            return employeesWithoutManger.get(0);
        }
    }

}
