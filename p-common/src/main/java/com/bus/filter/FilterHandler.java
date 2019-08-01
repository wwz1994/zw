package com.bus.filter;


import com.bus.context.springContext;
import com.bus.session.SessionHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 15:17
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class FilterHandler implements Filter {
    SessionHandler sessionHandler = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         sessionHandler = (SessionHandler)springContext.getBean("sessionHandler");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       HttpServletRequest req = (HttpServletRequest)servletRequest;
       HttpServletResponse response = (HttpServletResponse)servletResponse;
        sessionHandler.dofilter(req,response);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
