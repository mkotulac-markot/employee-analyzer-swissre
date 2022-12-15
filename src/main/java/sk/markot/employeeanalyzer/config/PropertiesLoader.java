package sk.markot.employeeanalyzer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesLoader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();

        try {
            ClassLoader classLoader = PropertiesLoader.class.getClassLoader();
            properties.load(classLoader.getResourceAsStream(fileName));
        } catch (IOException | NullPointerException e) {
            logger.error("Cannot read properties file: {}", fileName, e);
            throw new RuntimeException(e);
        }

        return properties;
    }
}
