package org.lld.logger.appenders;

import org.lld.logger.model.LogMessage;

public interface LogAppender {
    void append(LogMessage message);
}
