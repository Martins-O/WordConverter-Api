package com.martins.wordtopdf.exception;

import java.io.IOException;

public class StorageException extends ConversionException {
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
