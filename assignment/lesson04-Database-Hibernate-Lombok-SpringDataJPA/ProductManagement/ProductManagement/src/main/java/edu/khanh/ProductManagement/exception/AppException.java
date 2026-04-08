package edu.khanh.ProductManagement.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final int errorCode;
    public AppException(int code,String message) {
        super(message);
        this.errorCode =code;
    }

}
