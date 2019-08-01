package com.bus.session;

import com.bus.constant.Constant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 14:15
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class SessionUtils implements ISessionFilter{
    private  String token;
    public static ThreadLocal<SessionUtils> local;
    private static SessionUtils sessionUtils;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private static final String cookieName = "Session-Id";
    private static ConcurrentMap concurrentMap ;
    private  boolean isLogin=false;
    static {
        local = new ThreadLocal<SessionUtils>();
        concurrentMap = new ConcurrentHashMap<>();
    }


    public  void setSession(){
        local.set(this);
    }

    public static SessionUtils getSession(){
       return local.get();
    }

    public  String getToken() {
        return token;
    }

    public  void setToken(String token) {
        this.token = token;
        setSession();
    }
    public void SetLogin(){
        this.isLogin = true;
    }
    public boolean isLogin(){
        return this.isLogin;
    }
    @Override
    public void doFilter(HttpServletRequest request,HttpServletResponse response) {
        this.request = request;
        this.response = response;
        Handler(request,response);
        setSession();

    }
    private void Handler(HttpServletRequest request,HttpServletResponse response){
        boolean flag = false;
       Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for(Cookie cookie:cookies){
              if(cookieName.equals(cookie.getName())){
                  flag = true;
                  break;
              }
            }
            if(!flag){
                createCookie(response);
            }
        }else{
            createCookie(response);
        }
    }
    public void createCookie(HttpServletResponse response){
        String uuid = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(cookieName,uuid);
        concurrentMap.put(uuid, uuid);
        response.addCookie(cookie);
    }

    public static ConcurrentMap getConcurrentMap() {
        return concurrentMap;
    }

   /* public static boolean isIsLogin() {
        return isLogin;
    }

    public  void setIsLogin(boolean isLogin) {
        SessionUtils.isLogin = isLogin;
        setSession();
    }*/
}
