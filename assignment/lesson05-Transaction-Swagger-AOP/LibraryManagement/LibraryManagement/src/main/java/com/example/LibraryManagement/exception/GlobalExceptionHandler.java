package com.example.LibraryManagement.exception;

import com.example.LibraryManagement.dto.response.ApiRespone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiRespone<Void>> handleAppException(AppException ex){
        return ResponseEntity
                .status(ex.getErrorCode())
                .body(ApiRespone.error(ex.getErrorCode(),ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiRespone<Map<String,String>>> handleValidation(MethodArgumentNotValidException ex){
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getCode(),fieldError.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiRespone<>(400,"Dữ liệu không hợp lệ", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiRespone<Void>> handleException(Exception ex){
        return ResponseEntity
                .status(500)
                .body(ApiRespone.error(500, "Lỗi hệ thống. Vui lòng thử lại sau."));
    }

}
