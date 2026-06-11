package org.lld.logger.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lld.logger.enums.LogLevel;



public class LogMessage {
    private LogLevel level;
    private String message;
    private long timestamp;

    public LogMessage(LogLevel level, String message, long l) {
        this.level=level;
        this.message=message;
        this.timestamp=l;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
