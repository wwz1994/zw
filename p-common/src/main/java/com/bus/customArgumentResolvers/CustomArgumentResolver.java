package com.bus.customArgumentResolvers;

import com.bus.annotation.RequestAttribute;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 17:57
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class CustomArgumentResolver implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter var1){
        Annotation[] annotations = var1.getParameterAnnotations();
        if(annotations!=null&&annotations.length>0){
            for(Annotation annotation:annotations){
                if(RequestAttribute.class.isInstance(annotation)){
                    return true;
                }
            }
        }
        return false;
     }
      public  Object resolveArgument(MethodParameter var1, ModelAndViewContainer var2, NativeWebRequest var3, WebDataBinderFactory var4) throws Exception{
          Annotation[] annotations = var1.getParameterAnnotations();
          if(annotations!=null&&annotations.length>0){
              for(Annotation annotation:annotations){
                  if(RequestAttribute.class.isInstance(annotation)){
                      RequestAttribute requestAttribute = (RequestAttribute)annotation;
                      HttpServletRequest httprequest = (HttpServletRequest) var3.getNativeRequest();
                      return httprequest.getAttribute(requestAttribute.value());
                  }
              }
          }
          return null;
      }


}
