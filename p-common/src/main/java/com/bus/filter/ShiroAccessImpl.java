package com.bus.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wwz on 2018-11-21.
 */
public class ShiroAccessImpl extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(this.isLoginRequest(request, response)) {
            if(this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            if(isAjax(request)){
                JSONObject result = new JSONObject();
                result.put("isLogin","false");
                response.getWriter().print(result);
            }else{
                this.saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }


    public static boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
      /*  String sessionid = sessionIdCookie.readValue(WebUtils.toHttp(request), WebUtils.toHttp(response));
        // clear JSESSIONID in URL if session id is not null 
        if(sessionid != null){
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionid);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        }*/
        super.issueSuccessRedirect(request, response);
    }
}