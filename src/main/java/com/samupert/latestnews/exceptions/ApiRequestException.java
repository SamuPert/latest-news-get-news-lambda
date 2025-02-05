package com.samupert.latestnews.exceptions;

import java.util.Objects;

public class ApiRequestException extends RuntimeException {
    private final int httpStatusCode;
    private final String message;

    public ApiRequestException(
            int httpStatusCode,
            String message
    ) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public int httpStatusCode() {
        return httpStatusCode;
    }

    public String message() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ApiRequestException) obj;
        return Objects.equals(this.httpStatusCode, that.httpStatusCode) &&
                Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpStatusCode, message);
    }

    @Override
    public String toString() {
        return "ApiRequestException[" +
                "httpStatusCode=" + httpStatusCode + ", " +
                "message=" + message + ']';
    }


}
