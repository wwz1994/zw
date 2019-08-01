package com.bus.annotation;

import java.lang.annotation.*;

/**
 * Created by wwz on 2018-11-30.
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {

    boolean isRequired() default false;

    String value();
}
