package com.bus.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.Validator;

/**
 * @author wwz
 * @date 2019-09-12
 * @descrption:
 */
public abstract class BaseController {
    @Autowired
    protected org.springframework.validation.Validator validator;
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(validator);
    }

}