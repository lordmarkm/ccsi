package com.ccsi.security.reference;

/**
 * @author mbmartinez
 */
public abstract class Roles {

    public static final String ADMIN = "ADMIN";

    public static String asRole(String role) {
        return "ROLE_" + role;
    }
}
