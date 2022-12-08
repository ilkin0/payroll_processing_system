package com.ilkinmehdiyev.payroll_process_system.exception;

import java.io.IOException;

public class FileParseException extends IOException {
    public FileParseException(String message) {
        super(message);
    }
}