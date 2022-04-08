package org.elysian.spring.training.config;

public final class Roles {

    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "MANAGER";

    public static String getUserRole() {
        return USER_ROLE;
    }

    public static String getAdminRole() {
        return ADMIN_ROLE;
    }
}
