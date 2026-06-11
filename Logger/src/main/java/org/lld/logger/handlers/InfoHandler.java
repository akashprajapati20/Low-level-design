package org.lld.logger.handlers;

import org.lld.logger.enums.LogLevel;

public class InfoHandler extends LogHandler {
    @Override
    public boolean canHandle(LogLevel level) {
        return level == LogLevel.INFO;
    }
}
