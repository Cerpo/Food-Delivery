package com.cerpo.fd;

import java.util.Date;

public final class AppUtils extends AppUtilsBase {
    public static Date getDate(Integer offSet) {
        return offSet == null ? new Date() : new Date(System.currentTimeMillis() + offSet);
    }

    public static Integer getJWTExpiry() {
        return getIntegerProperty("fd.jwtExpirationInMs", "1200000");
    }

    public static Boolean isDevModeEnabled() {
        return getBooleanProperty("fd.mode.dev", Boolean.FALSE.toString());
    }
}
