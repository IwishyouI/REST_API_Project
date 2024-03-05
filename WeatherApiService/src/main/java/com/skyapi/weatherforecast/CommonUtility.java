package com.skyapi.weatherforecast;

import javax.servlet.http.HttpServletRequest;

public class CommonUtility {


    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARED-FOR");

        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
