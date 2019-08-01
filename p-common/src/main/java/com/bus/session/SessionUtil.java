package com.bus.session;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 16:54
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class SessionUtil extends MockHttpSession {

    public static HttpSession getSession(HttpServletRequest request){
       return  request.getSession();
    }


}
