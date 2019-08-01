package com.bus.constant;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 16:44
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class Constant {
    public static StringBuilder builder = new StringBuilder();
    static {
        builder.append("/api/logout = logout\n" +
                       "/api/user/login=anon\n" +
                       "/api/user/validLogin=anon");
    }

    public static  String getDefinesUrl(){
        return builder.toString();
    }



}
