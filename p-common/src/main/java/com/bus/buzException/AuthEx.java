package com.bus.buzException;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 14:12
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class AuthEx extends RuntimeException {
    public AuthEx(String message){
        super(message);
    }
}
