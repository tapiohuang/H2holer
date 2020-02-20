package org.hycode.h2holer.server.utils;


import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ServerUtil {
    private static ApplicationContext applicationContext = null;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ServerUtil.applicationContext = applicationContext;
    }

    public static void setCookie(String name, String value, int maxValue, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxValue != 0) {
            cookie.setMaxAge(maxValue);
        } else {
            cookie.setMaxAge(1800);
        }
        httpServletResponse.addCookie(cookie);
        try {
            httpServletResponse.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
