package com.bus.cookie;

import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wwz on 2018-11-20.
 */
public class CustomCookie extends SimpleCookie {

    @Override
    public void saveTo(HttpServletRequest request, HttpServletResponse response) {
        String name = this.getName();
        String value = this.getValue();
        String comment = this.getComment();
        String domain = this.getDomain();
        String path = this.calculatePath(request);
        int maxAge = this.getMaxAge();
        int version = this.getVersion();
        boolean secure = this.isSecure();
        boolean httpOnly = this.isHttpOnly();
        this.addCookieHeader(response, name, value, comment, domain, path, maxAge, version, secure, httpOnly);
    }
    private String calculatePath(HttpServletRequest request) {
        String path = StringUtils.clean(this.getPath());
        if(!StringUtils.hasText(path)) {
            path = StringUtils.clean(request.getContextPath());
        }

        if(path == null) {
            path = "/";
        }

        return path;
    }
    private void addCookieHeader(HttpServletResponse response, String name, String value, String comment, String domain, String path, int maxAge, int version, boolean secure, boolean httpOnly) {
        String headerValue = this.buildHeaderValue(name, value, comment, domain, path, maxAge, version, secure, httpOnly);
        response.addHeader("Cookie", headerValue);

    }

}