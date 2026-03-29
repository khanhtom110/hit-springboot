package com.example.demolession02.exception;

public class BadRequestException extends AppException{
    public BadRequestException(int code, String message) {
        super(400, message);
    }
}
