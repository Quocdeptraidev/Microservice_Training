package com.vn.userservice.config;

public class APIURL {
    public static final String[] PUBLIC_URLS = {
            "/api/users"};
    public static final String[] ADMIN_URLS = {"/api/admin/**"};
    public static final String[] USER_URLS = {"/api/user/**"};
    public static final String[] PROTECTED_URLS = {"/api/protected/**"};
}
