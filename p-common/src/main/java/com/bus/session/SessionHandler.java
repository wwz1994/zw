package com.bus.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 15:14
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class SessionHandler {
    private static ISessionFilter[] sessionFilters;

    public ISessionFilter[] getSessionFilters() {
        return sessionFilters;
    }

    public void setSessionFilters(ISessionFilter[] sessionFilters) {
        this.sessionFilters = sessionFilters;
    }
    public static boolean dofilter(HttpServletRequest request,HttpServletResponse response){
        for(ISessionFilter sessionFilter:sessionFilters){
            sessionFilter.doFilter(request,response);
        }
        return false;
    }
}
