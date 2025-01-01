package com.dct.base.constants;

public interface AuthConstants {

    String AUTHORITIES_KEY = "AUTHORITIES_KEY";
    String HEADER_SECURITY_POLICY = "default-src 'self'; frame-src 'self' data:; " +
            "script-src 'self' 'unsafe-inline' 'unsafe-eval' " +
            "https://storage.googleapis.com; " +
            "style-src 'self' 'unsafe-inline'; " +
            "img-src 'self' data:; " +
            "font-src 'self' data:";
    String HEADER_PERMISSIONS_POLICY = "camera=(), fullscreen=(self), geolocation=(), " +
            "gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()";

    String ROLE_ADMIN = "ADMIN";
    String ROLE_USER = "USER";
}
