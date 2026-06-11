package org.lld.logger.handlers;

import org.lld.logger.appenders.LogAppender;
import org.lld.logger.enums.LogLevel;
import org.lld.logger.model.LogMessage;

import java.util.ArrayList;
import java.util.List;

public abstract class LogHandler {
    protected LogHandler next;
    protected final List<LogAppender> observers = new ArrayList<>();

    public void setNext(LogHandler next) {
        this.next = next;
    }

    public LogHandler getNext() {
        return next;
    }

    public void subscribe(LogAppender observer) {
        observers.add(observer);
    }

    protected void notifyAllObservers(LogMessage message) {
        for (LogAppender observer : observers) {
            observer.append(message);
        }
    }

    public void handle(LogMessage message) {
        if (canHandle(message.getLevel())) {
            notifyAllObservers(message);
        }
        if (next != null) {
            next.handle(message);
        }
    }

    public abstract boolean canHandle(LogLevel level);
}
