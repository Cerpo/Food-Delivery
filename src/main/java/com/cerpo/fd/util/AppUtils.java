package com.cerpo.fd.util;

import com.cerpo.fd.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Date;

public final class AppUtils extends AppUtilsBase {
    public static Date getDate(Integer offSet) {
        return offSet == null ? new Date() : new Date(System.currentTimeMillis() + offSet);
    }

    public static User getCurrentlyAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    public static Integer getJWTExpiry() {
        return getIntegerProperty("fd.jwtExpirationInMs", "1200000");
    }

    public static Boolean isDevModeEnabled() {
        return getBooleanProperty("fd.mode.dev", Boolean.FALSE.toString());
    }
}
