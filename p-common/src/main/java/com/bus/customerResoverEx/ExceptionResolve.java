package com.bus.customerResoverEx;

import com.alibaba.fastjson.JSONObject;
import com.bus.buzException.AuthEx;
import com.bus.buzException.BuzEx;
import com.bus.utils.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 14:31
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class ExceptionResolve implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        write(e,httpServletResponse,httpServletRequest);
        return null;
    }

    private boolean isAjax(HttpServletRequest request){
        String header = request.getHeader("x-requested-with");
        if(!StringUtils.isEmpty(header)&&header.equalsIgnoreCase("XMLHttpRequest")){
           return true;
        }
        return false;
    }
    private void write(Exception e,HttpServletResponse response,HttpServletRequest request){
        PrintWriter printWriter = null;
        try{
            printWriter = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            if(e instanceof AuthEx){
                AuthEx authEx = (AuthEx)e;
                if(isAjax(request)){
                    response.setContentType("application/json;charset=UTF-8");
                    printWriter.write(getErrorInfo("403", authEx.getMessage()));
                }else{
                    response.setContentType("text/html;charset=UTF-8");
                    printWriter.append("<script> alert('"+authEx.getMessage()+"')</script>");
                }


            }else if(e instanceof BuzEx){
                BuzEx buzEx = (BuzEx)e;
                response.setContentType("application/json;charset=UTF-8");
                printWriter.write(getErrorInfo("406",buzEx.getMessage()));
            }else if(e instanceof  UnauthorizedException){
                response.setContentType("application/json;charset=utf-8");
                response.addHeader("Content-Type", "application/json;charset=UTF-8");
                printWriter.write(getErrorInfo("406","暂无权限"));
            }else if (e instanceof  Exception){
                response.setContentType("application/json;charset=utf-8");
                response.addHeader("Content-Type", "application/json;charset=UTF-8");
                printWriter.write(getErrorInfo("500","执行失败"));
            }
        }catch (IOException io){

        }finally {
            printWriter.flush();
            printWriter.close();
        }

    }

    private String getErrorInfo(String code,String message){
        JSONObject  map = new JSONObject();
        map.put("code",code);
        map.put("message",message);
        map.put("result",null);
        return map.toString();
    }
}
