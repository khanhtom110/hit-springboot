package vn.edu.khanhtom.fullflow_revise.exception;

public class BadRequestException extends AppException {
    public BadRequestException(String message) {
        super(400,message);
    }
}
