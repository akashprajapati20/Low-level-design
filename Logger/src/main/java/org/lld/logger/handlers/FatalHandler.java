package org.lld.logger.handlers;

import org.lld.logger.enums.LogLevel;

public class FatalHandler extends LogHandler {
    @Override
    public boolean canHandle(LogLevel level) {
        return level == LogLevel.FATAL;
    }
}
