package com.cerpo.fd.util;

import java.io.IOException;
import java.util.Properties;

public class AppUtilsBase {
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(AppUtilsBase.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Boolean getBooleanProperty(String propertyName, String defaultValue) {
        return Boolean.valueOf(properties.getProperty(propertyName, defaultValue));
    }

    protected static Integer getIntegerProperty(String propertyName, String defaultValue) {
        return Integer.valueOf(properties.getProperty(propertyName, defaultValue));
    }

    protected static Double getDoubleProperty(String propertyName, String defaultValue) {
        return Double.valueOf(properties.getProperty(propertyName, defaultValue));
    }
}
