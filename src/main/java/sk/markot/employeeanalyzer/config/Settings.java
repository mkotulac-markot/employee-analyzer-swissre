package sk.markot.employeeanalyzer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Settings {

    private static final Logger logger = LoggerFactory.getLogger(Settings.class);

    private final Integer managerSalarySurchargeLowerLimit;
    private final Integer managerSalarySurchargeUpperLimit;
    private final Integer maxEmployeeManagersInRow;

    public Settings(Properties properties) {
        try {
            this.managerSalarySurchargeLowerLimit = Integer.valueOf(properties.getProperty("manager.salary-surcharge.lower-limit"));
            this.managerSalarySurchargeUpperLimit = Integer.valueOf(properties.getProperty("manager.salary-surcharge.upper-limit"));
            this.maxEmployeeManagersInRow = Integer.valueOf(properties.getProperty("employee.managers.count.max"));
        } catch (NumberFormatException e) {
            logger.error("Invalid properties: {}", properties, e);
            throw new RuntimeException(e);
        }
    }

    public Integer getManagerSalarySurchargeLowerLimit() {
        return managerSalarySurchargeLowerLimit;
    }

    public Integer getManagerSalarySurchargeUpperLimit() {
        return managerSalarySurchargeUpperLimit;
    }

    public Integer getMaxEmployeeManagersInRow() {
        return maxEmployeeManagersInRow;
    }
}
