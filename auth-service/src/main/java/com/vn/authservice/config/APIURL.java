package com.vn.authservice.config;

public class APIURL {
    public static final String[] PUBLIC_URLS = {
            "/",
            "/public/**",
            "/oauth2/authorization/**",
            "/api/account/oauth2/success",
            "/api/account/login",
            "/api/account/register",
            "/api/account/**"};
    public static final String[] ADMIN_URLS = {"/api/admin/**"};
    public static final String[] USER_URLS = {"/api/user/**"};
    public static final String[] PROTECTED_URLS = {"/api/protected/**"};
}
