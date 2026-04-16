package com.example.LibraryManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRespone<T> {
    private int code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiRespone(int code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
        this.timestamp=LocalDateTime.now();
    }

    public ApiRespone(int code,String message){
        this(code,message,null);
    }

    public static<T> ApiRespone<T> success(T data){
        return new ApiRespone<>(200,"Thành công",data);
    }
    public static<T> ApiRespone<T> created(T data){
        return new ApiRespone<>(201,"Tạo thành công",data);
    }
    public static<T> ApiRespone<T> error(int code,String message){
        return new ApiRespone<>(code,message);
    }
}
