package com.sad.web.exception;

import lombok.Data;

@Data
public final class ServiceException extends  RuntimeException{
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    public ServiceException()
    {
    }
    public ServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;

    }
    public ServiceException(Integer code) {
        this.code = code;


    }
    public ServiceException(String message) {

        this.message = message;

    }



}
