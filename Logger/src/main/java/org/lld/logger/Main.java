package org.lld.logger;

import org.lld.logger.appenders.ConsoleAppender;
import org.lld.logger.appenders.FileAppender;
import org.lld.logger.enums.LogLevel;
import org.lld.logger.formatter.PlainTextFormatter;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();

        LogHandlerConfiguration.addAppenderForLevel(
                LogLevel.INFO,
                new ConsoleAppender(new PlainTextFormatter())
        );

        LogHandlerConfiguration.addAppenderForLevel(
                LogLevel.ERROR,
                new ConsoleAppender(new PlainTextFormatter())
        );

        LogHandlerConfiguration.addAppenderForLevel(
                LogLevel.ERROR,
                new FileAppender(new PlainTextFormatter(), "logs.txt")
        );

        // Usage
        logger.info("This is some key information"); // CONSOLE
        logger.error("Oh no! there's an error");     // CONSOLE + FILE
    }
}
