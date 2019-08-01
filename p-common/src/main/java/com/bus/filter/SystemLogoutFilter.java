package com.bus.filter;

import com.bus.redis.RedisCacheManager;
import com.bus.redis.RedisUtils;
import com.bus.utils.CookiesUtil;
import com.bus.utils.StringUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wwz on 2018-11-20.
 */
public class SystemLogoutFilter extends LogoutFilter {
    @Autowired
    private RedisTemplate redisTemplate;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //在这里执行退出系统前需要清空的数据
        Subject subject = getSubject(request, response);

        String redirectUrl = getRedirectUrl(request, response, subject);

        try {
            subject.logout();
            ShiroHttpServletRequest request1 =(ShiroHttpServletRequest) request;
            Cookie cookie =  CookiesUtil.getCookieByName(request1, "SHIROJSESSEION");
            String key = "shiro_redis_session:"+cookie.getValue();
            if(redisTemplate.hasKey(key)){
                redisTemplate.delete(key);
            }

            //RedisUtils.removeKey("shiro_redis_session:"+cookie.getValue());
        } catch (SessionException ise) {

            ise.printStackTrace();

        }
        if(!StringUtils.isEmpty(url)){
            redirectUrl = url;
        }
        issueRedirect(request, response, redirectUrl);

        //返回false表示不执行后续的过滤器，直接返回跳转到登录页面

        return false;

    }

}
