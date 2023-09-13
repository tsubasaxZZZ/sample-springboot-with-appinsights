package com.example.demo;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.Logger;


public class AccessLogger {
    public static void log(Logger logger, String requestURI, int responseCode, long responseTime) {
        try {
            InetAddress clientAddress = InetAddress.getLocalHost();
            String clientIP = clientAddress.getHostAddress();
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String logMessage = String.format("%s %s %s %d %d", timestamp, clientIP, requestURI, responseCode, responseTime);
            logger.info(logMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
