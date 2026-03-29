package vn.edu.khanhtom.fullflow_revise.dto.respone;

import java.time.LocalDate;

public class ApiResponse<T> {
    private int code;
    private String message;;
    private T data;
    private LocalDate timestamp;

    public ApiResponse() {
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDate.now();
    }

    public ApiResponse(int code, String message) {
        this(code,message,null);
    }

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(200,"Thang cong",data);
    }
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(201,"Tao thanh cong",data);
    }
    public static  <T> ApiResponse<T> success(){
        return new ApiResponse<>(204,"Thanh cong",null);
    }
    public static <T> ApiResponse<T> error(int code,String message){
        return new ApiResponse<>(code,message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
}
