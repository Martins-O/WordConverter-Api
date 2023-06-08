package com.martins.wordtopdf.exception;

public class ConversionException extends RuntimeException {
    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
