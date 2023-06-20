package de.civento.eahtools.routingrepository.base.logging;

public enum ErrorCode {
    SECURITY_ERROR(1000, "Security error"),
    REQUEST_FAILED(2000, "Request failed"),
    CODING_ERROR(9000, "Coding error");

    private final int value;
    private final String text;

    ErrorCode(int value, String text) {


        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "LogCode: " + getValue() + " | " + getText();
    }

    public String toStringForException() {
        return "LogCode: " + getValue();
    }
}
