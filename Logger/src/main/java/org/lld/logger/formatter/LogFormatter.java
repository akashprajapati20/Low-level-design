package org.lld.logger.formatter;

import org.lld.logger.model.LogMessage;

public interface LogFormatter {
    String format(LogMessage message);
}
