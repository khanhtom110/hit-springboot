package vn.edu.khanhtom.StudentManagement.exception;

public class BadRequestException extends AppException{
    public BadRequestException(int code, String message) {
        super(code, message);
    }
}
