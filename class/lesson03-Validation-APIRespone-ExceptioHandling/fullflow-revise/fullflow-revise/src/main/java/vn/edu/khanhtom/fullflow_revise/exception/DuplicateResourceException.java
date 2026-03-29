package vn.edu.khanhtom.fullflow_revise.exception;

public class DuplicateResourceException extends AppException{
    public DuplicateResourceException(String resource, String field, Object value) {
        super(409, String.format("%s da ton tai voi %s: '%s'",resource,field,value));
    }
}
