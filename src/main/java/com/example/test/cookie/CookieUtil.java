package com.example.test.cookie;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    public static Cookie createCookie(String accessToken) {

        Cookie cookie = new Cookie("accessToken", accessToken);

        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);

        return cookie;

    }
}
