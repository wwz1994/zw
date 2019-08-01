package com.bus.annotation;

import javax.lang.model.element.Element;
import java.lang.annotation.*;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 18:04
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestAttribute {
    String value() default "";
}
