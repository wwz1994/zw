package com.bus.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 15:10
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public interface ISessionFilter  {
    public void doFilter(HttpServletRequest request,HttpServletResponse response);
}
