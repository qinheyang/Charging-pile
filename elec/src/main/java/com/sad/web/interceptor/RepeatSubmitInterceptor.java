package com.sad.web.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.sad.web.annotations.RepeatSubmit;
import com.sad.web.entity.ResponseResult;

import com.sad.web.utils.ServletUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public  abstract  class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod)
        {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null)
            {
                if (this.isRepeatSubmit(request))
                {
                    ResponseResult result = ResponseResult.error("不允许重复提交，请稍后再试");
                    ServletUtil.renderString(response, JSONObject.toJSONString(result));
                    return false;
                }
            }
            return true;
        }
        else
        {
            return super.preHandle(request, response, handler);
        }

    }

    public abstract boolean isRepeatSubmit(HttpServletRequest request);
}
