package com.bus.interceptors.impl;

import com.bus.interceptors.Interceptor;
import com.bus.redis.RedisUtils;
import com.bus.userInfo.ShiroPrincipal;
import com.bus.utils.CookiesUtil;
import com.bus.utils.ObjectUtils;
import com.bus.utils.SubjectUtils;
import com.bus.vo.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 10:01
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
@Component
public class WebInterceptor implements Interceptor {
    private final static  String permission = "_permission:";
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean interceptor(Method method, HttpServletRequest request, HttpServletResponse response,List<String> list) {
        Cookie cookie = CookiesUtil.getCookieByName(request,"SHIROJSESSEION");
        if(cookie == null){
            return true;
        }
        String sessionId = cookie.getValue();
        User object = (User) redisTemplate.opsForValue().get("user:info:"+sessionId);
        if(object!=null){
            new ShiroPrincipal(object);
        }else{
            if(list.contains(request.getRequestURI())){
                return true;
            }
            SecurityUtils.getSubject().logout();
            return false;
        }
        return true;
    }


    private List<String> getPermission(){
        String key = permission+ SubjectUtils.getUser().getUserName();
        List<String> permissions = RedisUtils.getList(key);
        if(ObjectUtils.isNull(permissions)){
            permissions = SubjectUtils.getPermission();
            RedisUtils.setList(key,permissions);
           return  permissions;
        }
        return permissions;
    }
}
