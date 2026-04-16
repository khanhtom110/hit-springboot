package com.example.LibraryManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRespone<T> {
    private int code;
    private String message;
    private T data;
    private LocalDateTime timestamp;


}
