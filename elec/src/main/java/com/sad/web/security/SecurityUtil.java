package com.sad.web.security;

import com.sad.web.constant.HttpStatus;
import com.sad.web.entity.system.LoginUser;
import com.sad.web.exception.ServiceException;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "获取用户信息异常");
        }
    }

    public static Long getUserId() {
        return getLoginUser().getSysUser().getUserId();
    }


}
