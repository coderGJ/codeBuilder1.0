package com.guojun.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class AppProperties {

    private static Properties properties = null;

    private static Logger logger = LoggerFactory.getLogger(AppProperties.class);

    private AppProperties() {
        super();
    }

    static {
        loadProperties();
    }

    /**
     * @param name The property name
     * @return specified property value
     */
    public static String getProperty(String name) {
        return properties.getProperty(name);
    }
    
    /**
     * @param name The property name
     * @param defaultVal The property value is null return defaultVal
     * @return specified property value
     */
    public static String getProperty(String name, String defaultVal) {
        return getNotNullValue(properties.getProperty(name), defaultVal);
    }

    /**
     * @param value
     * @param defaultVal
     * @return not null string
     */
    private static String getNotNullValue(String value, String defaultVal) {
        if (value == null) {
            if (defaultVal != null) {
                return defaultVal;
            }
            return "";
        }
        return value;
    }
    /**
     * Load properties.
     */
    private static void loadProperties() {

        InputStream is = null;
        try {
            File propsFile = new File("/config.properties");
            is = new FileInputStream(propsFile);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        if (is == null) {
            try {
                is = AppProperties.class.getResourceAsStream("/config.properties");
            } catch (Throwable t) {
                handleThrowable(t);
            }
        }
        if (is != null) {
            try {
                properties = new Properties();
                properties.load(is);
            } catch (Throwable t) {
                handleThrowable(t);
                logger.debug("出错啦");
            } finally {
                try {
                    is.close();
                } catch (IOException ioe) {
                    logger.debug("Could not close config.properties");
                }
            }
        } else {
            // Do something
            logger.error("Failed to load config.properties");
            // That's fine - we have reasonable defaults.
            properties = new Properties();
        }

    }
    
    // Copied from ExceptionUtils since that class is not visible during start
    private static void handleThrowable(Throwable t) {
        if (t instanceof ThreadDeath) {
            throw (ThreadDeath) t;
        }
        if (t instanceof VirtualMachineError) {
            throw (VirtualMachineError) t;
        }
    }
}
