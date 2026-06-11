package org.lld.logger;

import org.lld.logger.appenders.LogAppender;
import org.lld.logger.enums.LogLevel;
import org.lld.logger.handlers.DebugHandler;
import org.lld.logger.handlers.ErrorHandler;
import org.lld.logger.handlers.FatalHandler;
import org.lld.logger.handlers.InfoHandler;
import org.lld.logger.handlers.LogHandler;
import org.lld.logger.handlers.WarnHandler;
import org.lld.logger.model.LogMessage;

public class LogHandlerConfiguration {
    private static final LogHandler CHAIN = buildChain();

    private LogHandlerConfiguration() {
    }

    private static LogHandler buildChain() {
        LogHandler debug = new DebugHandler();
        LogHandler info = new InfoHandler();
        LogHandler warn = new WarnHandler();
        LogHandler error = new ErrorHandler();
        LogHandler fatal = new FatalHandler();

        debug.setNext(info);
        info.setNext(warn);
        warn.setNext(error);
        error.setNext(fatal);

        return debug;
    }

    public static void addAppenderForLevel(LogLevel level, LogAppender appender) {
        LogHandler current = CHAIN;
        while (current != null) {
            if (current.canHandle(level)) {
                current.subscribe(appender);
                return;
            }
            current = current.getNext();
        }
    }

    public static void handle(LogMessage message) {
        CHAIN.handle(message);
    }
}
