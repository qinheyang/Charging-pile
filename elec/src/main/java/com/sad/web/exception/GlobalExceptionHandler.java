package com.sad.web.exception;

import com.sad.web.constant.HttpStatus;
import com.sad.web.entity.ResponseResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseResult handleServiceException(ServiceException e, HttpServletRequest request){

        Integer code = e.getCode();
        log.error(e.getMessage(), e);
        return Objects.isNull(code) ? new ResponseResult(HttpStatus.ERROR,e.getMessage()) :new ResponseResult(code,e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return ResponseResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return ResponseResult.error(e.getMessage());
    }
}
