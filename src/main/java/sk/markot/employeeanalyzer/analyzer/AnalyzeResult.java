package sk.markot.employeeanalyzer.analyzer;

import java.util.*;

public class AnalyzeResult {

    private final Map<AnalyzeResultType, List<String>> results;

    public AnalyzeResult() {
        results = new HashMap<>();
    }

    public void addEmployee(AnalyzeResultType resultType, String employeeId) {
        results.computeIfAbsent(resultType, k -> new ArrayList<>()).add(employeeId);
    }

    public List<String> getResults(AnalyzeResultType resultType) {
        return results.getOrDefault(resultType, Collections.emptyList());
    }
}
