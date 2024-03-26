package com.sad.web.entity;

import com.sad.web.constant.HttpStatus;

import java.util.HashMap;


public class ResponseResult extends HashMap<String, Object> {
    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";

    public ResponseResult(int code, String msg) {
        super.put(CODE_TAG,code);
        super.put(MSG_TAG,msg);
    }

    public ResponseResult(int code, String msg, Object data) {
        super.put(CODE_TAG,code);
        super.put(MSG_TAG,msg);
        super.put(DATA_TAG,data);
    }


    public static ResponseResult success()
    {
        return ResponseResult.success("操作成功");
    }

    public static ResponseResult success(String msg)
    {
        return  new ResponseResult(HttpStatus.SUCCESS,msg);
    }

    public static ResponseResult success(Object data)
    {
        return ResponseResult.success("操作成功",data);
    }

    public static ResponseResult success(String msg, Object data) {
        return  new ResponseResult(HttpStatus.SUCCESS,msg,data);
    }


    public static ResponseResult error()
    {
        return ResponseResult.success("操作失败");
    }

    public static ResponseResult error(String msg)
    {
        return  new ResponseResult(HttpStatus.ERROR,msg);
    }

    public static ResponseResult error(Object data)
    {
        return ResponseResult.error("操作失败",data);
    }

    public static ResponseResult error(String msg, Object data) {
        return  new ResponseResult(HttpStatus.ERROR,msg,data);
    }

    public ResponseResult put(String key, Object value) {
        super.put(key,value);
        return this;
    }




}
