package edu.khanh.ProductManagement.exception;

public class BadException extends AppException{
    public BadException(String message) {
        super(400, message);
    }
}
