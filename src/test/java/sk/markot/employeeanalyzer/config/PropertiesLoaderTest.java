package sk.markot.employeeanalyzer.config;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class PropertiesLoaderTest {

    @Test
    void testLoadProperties() {
        String fileName = "testConfig.properties";

        Properties properties = PropertiesLoader.loadProperties(fileName);

        assertThat(properties)
                .containsOnlyKeys(
                        "manager.salary-surcharge.lower-limit",
                        "manager.salary-surcharge.upper-limit",
                        "employee.managers.count.max")
                .containsValues("10", "30", "3");
    }
}