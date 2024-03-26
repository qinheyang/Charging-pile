package com.sad.web.service.impl.system;

import com.sad.web.constant.RedisKeyConstants;
import com.sad.web.entity.system.LoginUser;

import com.sad.web.entity.system.SysUser;
import com.sad.web.exception.ServiceException;

import com.sad.web.redis.RedisCache;
import com.sad.web.service.system.ILoginService;
import com.sad.web.utils.JwtUtil;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 登录实现类
 */
@Service
public class LoginServiceImpl implements ILoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;

    //用户登录
    @Override
    public String login(SysUser user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new ServiceException("用户名或密码错误");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getSysUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把token响应给前端
        redisCache.setCacheObject(RedisKeyConstants.LOGIN_KEY + userId, loginUser);
        return jwt;
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getSysUser().getUserId();
        redisCache.deleteObject("login:"+userId);
    }
}
