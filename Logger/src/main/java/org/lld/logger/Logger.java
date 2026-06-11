package org.lld.logger;

import org.lld.logger.appenders.LogAppender;
import org.lld.logger.enums.LogLevel;
import org.lld.logger.model.LogMessage;

public class Logger {
    private static Logger INSTANCE;

    private Logger() {
    }

    public static synchronized Logger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Logger();
        }
        return INSTANCE;
    }

    public void addAppenderForLevel(LogLevel level, LogAppender appender) {
        LogHandlerConfiguration.addAppenderForLevel(level, appender);
    }

    public void log(LogLevel level, String message) {
        LogMessage logMessage = new LogMessage(level, message, System.currentTimeMillis());
        LogHandlerConfiguration.handle(logMessage);
    }

    public void trace(String message) {
        log(LogLevel.TRACE, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }
}
