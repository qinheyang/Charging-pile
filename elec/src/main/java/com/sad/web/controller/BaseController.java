package com.sad.web.controller;

import com.sad.web.entity.system.LoginUser;
import com.sad.web.security.SecurityUtil;

public class BaseController {
    public static LoginUser getCurrentLoginUser(){
            return SecurityUtil.getLoginUser();
    }

    public static Long getCurrentUserId(){
        return SecurityUtil.getLoginUser().getSysUser().getUserId();
    }
    public static String getCurrentLoginUserName(){
        return SecurityUtil.getLoginUser().getUsername();
    }
}
