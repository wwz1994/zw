package com.bus.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import org.springframework.validation.Validator;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wwz
 * @date 2019-09-12
 * @descrption:
 */
public abstract class BaseController {
    @Autowired
    protected Validator validator;

   /* @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(validator);
        webDataBinder.registerCustomEditor(Date.class,new PropertyEditorSupport(new SimpleDateFormat("yyyy-MM-dd")));
    }
*/
}