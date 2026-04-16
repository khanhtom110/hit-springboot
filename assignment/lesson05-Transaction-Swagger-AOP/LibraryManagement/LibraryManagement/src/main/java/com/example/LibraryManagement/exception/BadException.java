package com.example.LibraryManagement.exception;

public class BadException extends AppException{
    public BadException(String message) {
        super(400, message);
    }
}
