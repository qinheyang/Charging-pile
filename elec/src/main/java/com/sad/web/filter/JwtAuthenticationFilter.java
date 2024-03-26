package com.sad.web.filter;

import com.sad.web.constant.RedisKeyConstants;
import com.sad.web.constant.SystemConstant;
import com.sad.web.entity.system.LoginUser;
import com.sad.web.redis.RedisCache;
import com.sad.web.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(SystemConstant.TOKEN);
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String userId;

        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LoginUser user = redisCache.getCacheObject(RedisKeyConstants.LOGIN_KEY + userId);

        if (Objects.isNull(user)) {
            throw new RuntimeException("用户未登录");
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }


}
