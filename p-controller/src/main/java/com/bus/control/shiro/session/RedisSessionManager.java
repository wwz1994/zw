package com.bus.control.shiro.session;

import com.bus.redis.RedisUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

/**
 * Created by wwz on 2018-11-19.
 */
public class RedisSessionManager extends DefaultWebSessionManager {
    @Override
    public Session start(SessionContext context) {
        StoreSessionId(context.getSessionId().toString());
        return null;
    }

    private void StoreSessionId(String sessionId){
        RedisUtils.setString(sessionId, sessionId);
    }
    @Override
    public Session getSession(SessionKey key) throws SessionException {
        return null;
    }
}