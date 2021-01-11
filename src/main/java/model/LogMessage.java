package model;

public class LogMessage {
    private final String message;
    private final LogLevel level;

    public LogMessage(String message, LogLevel level) {
        this.message = message + "\n";
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLevel() {
        return level;
    }
}

