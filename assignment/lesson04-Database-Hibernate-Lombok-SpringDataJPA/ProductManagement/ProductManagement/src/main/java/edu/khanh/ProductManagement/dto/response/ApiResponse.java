package edu.khanh.ProductManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse(int code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
        this.timestamp=LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(200,"Thành công",data);
    }

    public static <T> ApiResponse<T> created(T data){
        return new ApiResponse<>(201,"Tạo thành công",data);
    }

    public static <T> ApiResponse<T> error(int code,String message){
        return new ApiResponse<>(code,message,null);
    }
}
