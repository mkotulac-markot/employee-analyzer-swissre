package sk.markot.employeeanalyzer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

public final class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static File getResourceFile(String fileName) throws FileNotFoundException {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new FileNotFoundException("File: +" + fileName + " not found");
        }
        try {
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            logger.error("Cannot read file: {}", fileName, e);
            throw new RuntimeException(e);
        }
    }
}
