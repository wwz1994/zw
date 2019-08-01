package com.bus.interceptors;

import com.bus.buzException.AuthEx;
import com.bus.buzException.BuzEx;
import com.bus.redis.RedisCacheManager;
import com.bus.utils.CookiesUtil;
import com.bus.utils.StringUtils;
import com.bus.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author  wwz on 2019-03-24.
 */
@Component
public class MoreSubmitInterceptor implements Interceptor {
    private final static String IP_FLAG = "user_ip_flag:";
    @Autowired
    private RedisTemplate redisTemplate;
    private HttpServletResponse response;

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    private  String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Override
    public boolean interceptor(Method method, HttpServletRequest request, HttpServletResponse response,List<String> list)throws BuzEx {
        if(request.getRequestURI().contains("api/user/validLogin")){
            return true;
        }
        String userId = getIpAddress(request);
        String key = IP_FLAG+userId;
        long milliseconds = Calendar.getInstance().getTime().getTime();
        if(!redisTemplate.hasKey(key)){
            redisTemplate.opsForValue().set(key, milliseconds,3600, TimeUnit.SECONDS);
            return true;
        }else{
            Object value = redisTemplate.opsForValue().get(key);
            redisTemplate.delete(key);
            redisTemplate.opsForValue().set(key, milliseconds,3600, TimeUnit.SECONDS);
            long seconds = (milliseconds-(long)value)/(1000);
            if(seconds<5){
                throw new BuzEx("操作太频繁");
            }
        }
        this.response = response;
        setClientIp(userId);
        return true;
    }
    private void setClientIp(String value){
        Cookie cookie = new Cookie("ip",value);
        cookie.setPath("/");
        cookie.setSecure(true);
        this.response.addCookie(cookie);
    }
}