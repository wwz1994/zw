package com.bus.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by wwz on 2019-07-02.
 */

public class CustomAccessControlFilter extends AccessControlFilter {

    @Autowired
    private RedisTemplate redisTemplate;
    private  static String keyPrefix = "shiro_redis_session:";

    private String webLoginUrl;
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if(!isLogin()){
            WebUtils.issueRedirect(request, response, webLoginUrl);
            return false;
        }
        return true;
    }

    private boolean isLogin()throws Exception{
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            return false;
        }
        if(subject==null||subject.getSession()==null||subject.getSession().getId()==null){
            return false;
        }
        Session session = (Session) redisTemplate.opsForValue().get(getSessionIdName(subject.getSession().getId()));
        if (session == null) {
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(!isLogin()) {
            return false;
        }
        return true;
    }

    private static  String getSessionIdName(Serializable id){
        return keyPrefix+id;
    }
    public String getWebLoginUrl() {
        return webLoginUrl;
    }

    public void setWebLoginUrl(String webLoginUrl) {
        this.webLoginUrl = webLoginUrl;
    }
}