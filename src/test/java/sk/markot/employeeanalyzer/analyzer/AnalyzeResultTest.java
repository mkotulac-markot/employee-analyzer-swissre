package sk.markot.employeeanalyzer.analyzer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static sk.markot.employeeanalyzer.analyzer.AnalyzeResultType.*;


class AnalyzeResultTest {

    @Test
    void testAddEmployee() {
        String employeeId = "1";

        AnalyzeResult result = new AnalyzeResult();
        for (AnalyzeResultType resultType : AnalyzeResultType.values()) {
            result.addEmployee(resultType, employeeId);
        }

        assertThat(result.getResults(MAX_SALARY)).containsExactly("1");
        ;
        assertThat(result.getResults(MIN_SALARY)).containsExactly("1");
        ;
        assertThat(result.getResults(REPORTING_LINE)).containsExactly("1");
        ;
    }
}