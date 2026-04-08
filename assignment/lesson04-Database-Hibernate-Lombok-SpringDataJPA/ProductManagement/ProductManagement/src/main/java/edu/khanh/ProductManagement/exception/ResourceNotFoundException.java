package edu.khanh.ProductManagement.exception;

public class ResourceNotFoundException extends AppException{
    public ResourceNotFoundException(String resource,String field,Object value) {
        super(404,String.format("%s không được tìm thấy với %s: '%s'",resource,field,value));
    }
}
