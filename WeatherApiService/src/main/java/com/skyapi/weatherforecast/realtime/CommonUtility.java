package com.skyapi.weatherforecast.realtime;

import javax.servlet.http.HttpServletRequest;

public class CommonUtility {


    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
