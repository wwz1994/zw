package com.bus.utils;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import com.bus.vo.User;

import java.util.List;

/**
 * Created by wwz on 2018-11-30.
 */
public class SubjectUtils {
    private static final  Object key = "user_info";
    private static final  Object key_ = "permission_";
    private static  Session session;
    private static Subject subject;


    public static User getUser(){
        return (User)getSession().getAttribute(key);
    }
    private static Session getSession(){
        if(session == null){
            session = subject.getSession();
        }
       return session;
    }
    public static void setUser(User user,Subject subject){
        SubjectUtils.subject = subject;
        getSession().setAttribute(key,user);
    }
    public static List<String> getPermission(){
        return (List)getSession().getAttribute(key_);
    }
    public static void setPermission(List<String> permission){
        getSession().setAttribute(key_,permission);
    }
}