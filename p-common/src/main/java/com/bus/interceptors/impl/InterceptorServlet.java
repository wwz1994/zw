package com.bus.interceptors.impl;

import com.bus.annotation.Auth;
import com.bus.interceptors.Interceptor;
import com.bus.utils.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 9:58
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class InterceptorServlet implements HandlerInterceptor {
    private Interceptor[] interceptorses;

    private String[] exclusivePath;


    public Interceptor[] getInterceptorses() {
        return interceptorses;
    }

    public void setInterceptorses(Interceptor[] interceptorses) {
        this.interceptorses = interceptorses;
    }

    public String[] getExclusivePath() {
        return exclusivePath;
    }

    public void setExclusivePath(String[] exclusivePath) {
        this.exclusivePath = exclusivePath;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if(!ObjectUtils.isNull(interceptorses)){
            HandlerMethod handlerMethod = (HandlerMethod)o;
            Method method = handlerMethod.getMethod();
            for(Interceptor interceptor:interceptorses){
                if(!interceptor.interceptor(method,httpServletRequest,httpServletResponse, Arrays.asList(exclusivePath))){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
