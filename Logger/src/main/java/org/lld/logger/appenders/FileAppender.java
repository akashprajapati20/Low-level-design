package org.lld.logger.appenders;

import org.lld.logger.formatter.LogFormatter;
import org.lld.logger.model.LogMessage;

import java.io.FileWriter;
import java.io.IOException;

public class FileAppender implements LogAppender {
    private final LogFormatter formatter;
    private final String fileName;

    public FileAppender(LogFormatter formatter, String fileName) {
        this.formatter = formatter;
        this.fileName = fileName;
    }

    @Override
    public void append(LogMessage message) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(formatter.format(message) + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Failed to write log to file '" + fileName + "': " + e.getMessage());
        }
    }
}
