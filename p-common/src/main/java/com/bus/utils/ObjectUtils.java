package com.bus.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 10:42
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class ObjectUtils {

    public static boolean isNull(Object o){
        if(o == null){
            return true;
        }
        if(o instanceof Map){
            Map map = (Map)o;
            if(map == null || map.size()<=0){
                return true;
            }
        }else if(o instanceof Collection){
            Collection collection = (Collection)o;
            if(collection == null || collection.size()<=0){
                return true;
            }
        }
        return false;
    }
}
