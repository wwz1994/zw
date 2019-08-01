package com.bus.interceptors.impl;

import com.alibaba.fastjson.JSONObject;
import com.bus.annotation.MRequest;
import com.bus.buzException.AuthEx;
import com.bus.interceptors.Interceptor;
import com.bus.session.SessionUtil;
import com.bus.session.SessionUtils;
import com.bus.utils.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 12:35
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class MInterceptor implements Interceptor {
    @Override
    public boolean interceptor(Method method, HttpServletRequest request, HttpServletResponse response,List<String> list) {
        if(method.isAnnotationPresent(MRequest.class)){
         /*   MRequest mRequest = method.getAnnotation(MRequest.class);

                try{
                    String content = process(request);
                    JSONObject object = JSONObject.parseObject(content);
                    request.setAttribute("body", object.getString("body"));
                    if(mRequest.isVlidate()) {
                        HttpSession session = SessionUtil.getSession(request);
                        if (ObjectUtils.isNull(session)) {
                            throw new AuthEx("please login");
                        }
                        String token = object.getString("token");
                        if (object == null || "".equals(token)) {
                            throw new AuthEx("param is null");
                        }
                        if (!token.equals(session.getAttribute("token"))) {
                            throw new AuthEx("token is error");
                        }
                    }
                }catch (IOException e){

                }
*/
        }
        return true;
    }

    public String process(HttpServletRequest request)throws IOException{
       int length =  request.getContentLength();
        byte[] bytes = null;
        if(length>0){
            InputStream inputStream = null;
                 inputStream = request.getInputStream();
                int available = inputStream.available();
                 bytes = new byte[length];
                int i = 0;
                while((i = inputStream.read(bytes))>0){

                }
        }
        return new String(bytes,"utf-8");
    }
}
