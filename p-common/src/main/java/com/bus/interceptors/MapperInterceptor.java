package com.bus.interceptors;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * Created by wwz on 2019-03-23.
 */
public class MapperInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        invocation.getArgs();
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}