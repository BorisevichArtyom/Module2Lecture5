package by.itacademy.javaenterprise.utils;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {
    public static String getIP(HttpServletRequest req) {
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        } else {
          ipAddress= ipAddress.split(";")[0];
        }

        return ipAddress;
    }
}