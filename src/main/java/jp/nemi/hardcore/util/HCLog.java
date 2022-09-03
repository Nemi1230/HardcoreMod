package jp.nemi.hardcore.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HCLog {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void info(String message) {
        log(Level.INFO, message);
    }

    public static void info(String message, Object object) {
        log(Level.INFO, message, object);
    }

    public static void all(String message) {
        log(Level.ALL, message);
    }

    public static void debug(String message) {
        log(Level.DEBUG, message);
    }

    public static void fatal(String message) {
        log(Level.FATAL, message);
    }

    public static void error(String message) {
        log(Level.ERROR, message);
    }

    public static void warn(String message) {
        log(Level.WARN, message);
    }

    public static void off(String message) {
        log(Level.OFF, message);
    }

    public static void trace(String message) {
        log(Level.TRACE, message);
    }

    private static void log(Level logLevel, String message) {
        LOGGER.log(logLevel, message);
    }

    private static void log(Level logLevel, String message, Object object) {
        LOGGER.log(logLevel, message, object);
    }
}
