package com.bus.annotation;

import java.lang.annotation.*;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 10:37
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    boolean isAuth() default false;
    String token() default "";
}
