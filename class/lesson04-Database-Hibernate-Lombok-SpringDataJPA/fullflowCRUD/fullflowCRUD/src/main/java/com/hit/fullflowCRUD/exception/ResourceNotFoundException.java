package com.hit.fullflowCRUD.exception;

public class ResourceNotFoundException extends AppException{
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(404, String.format("%s không tìm thấy với %s: '%s'", resource, field, value));
    }
}
