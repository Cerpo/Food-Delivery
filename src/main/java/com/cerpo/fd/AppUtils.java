package com.cerpo.fd;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public final class AppUtils {
    private static final Properties properties;

    static {
        properties = new Properties();
        try{
            properties.load(new FileInputStream("./src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Date getDate(Integer offSet) {
        return offSet == null ? new Date() : new Date(System.currentTimeMillis() + offSet);
    }

    public static Integer getJWTExpiry() {
        return Integer.valueOf(properties.getProperty("fd.jwtExpirationInMs"));
    }
}
