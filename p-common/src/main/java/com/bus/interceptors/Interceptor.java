package com.bus.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 9:55
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public interface Interceptor {
    public boolean interceptor(Method method, HttpServletRequest request, HttpServletResponse response,List<String> exclusivePath);
}
