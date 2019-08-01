package com.bus.control.shiro.session;

import com.bus.redis.RedisCacheManager;
import com.bus.redis.RedisUtils;
import com.bus.result.BuzCode;
import com.bus.utils.CookiesUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by wwz on 2018-11-19.
 */
@Component
public class RedisSessionDAO extends AbstractSessionDAO {
    private Long timeout=3600000L;
    public Long getTimeout() {
        return timeout;
    }
    @Autowired
    private RedisTemplate redisTemplate;;
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    private String keyPrefix = "shiro_redis_session:";

    @Override
    protected Serializable doCreate(Session session) {
        Serializable serializable = this.generateSessionId(session);
        this.assignSessionId(session,serializable);
        this.saveSessionId(session);
        session.setTimeout(timeout);
        return serializable;
    }
    private void saveSessionId(Session session){
        if(session == null || session.getId() == null){
            return;
        }
        redisTemplate.opsForValue().set(getSessionIdName(session.getId()), session,1, TimeUnit.HOURS);
       // session.setTimeout(RedisUtils.);
      //  RedisUtils.setBytes(getSessionIdName(session.getId()), getByte(session));
    }

    /** 
          * 对象转数组 
          * @param obj 
          * @return 
          */
    public static byte[] getByte(Object o){
        byte[] bytes = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(stream);
            outputStream.writeObject(o);
            outputStream.flush();
            bytes = stream.toByteArray();
            outputStream.close();
            stream.close();
        }catch (Exception e){

        }
        return bytes;

    }

    public static Object getObj(byte[] bytes){
        Object object = null;
        try{
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            object = objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        }catch (Exception e){
            System.out.println("=============");
            e.printStackTrace();
        }
        return object;

    }

    private String getSessionIdName(Serializable id){
        return this.keyPrefix+id;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = (Session)redisTemplate.opsForValue().get(getSessionIdName(sessionId));
            if(session == null){
                throw new UnknownSessionException(BuzCode.session_invalid.getMessage());
            }
             return  session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        try{
            String sessionName = getSessionIdName(session.getId());
            redisTemplate.opsForValue().set(sessionName, session,1, TimeUnit.HOURS);
           // RedisUtils.setBytes(sessionName,getByte(session));
        }catch (ClassCastException e ){
            System.out.println("=====++++++++++++++++====");
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Session session) {
        redisTemplate.delete(getSessionIdName(session.getId()));
       //RedisUtils.removeKey(getSessionIdName( session.getId()).getBytes());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return Collections.EMPTY_LIST;
    }

}