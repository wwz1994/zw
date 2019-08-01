package com.bus.result;

import com.bus.buzException.BuzEx;
import com.bus.constant.ResultCode;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 11:36
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class JsonResult {

    private String message;
    private Integer code;
    private Object result;
    private Boolean success;
    private Object o;
    public static JsonResult OK(){
        return new JsonResult(BuzCode.OK,true);
    }
    public static JsonResult OK(Object result){
        return new JsonResult(result,true);
    }

    public static JsonResult OK(Object result,Object o){
        return new JsonResult(result,true,o);
    }
    public JsonResult(Object result,boolean success){
        this.result = result;
        this.message = BuzCode.OK.getMessage();
        this.code = BuzCode.OK.getCode();
        this.success = success;
    }
    public JsonResult(Object result,boolean success,Object o){
        this.result = result;
        this.o = o;
        this.message = BuzCode.OK.getMessage();
        this.code = BuzCode.OK.getCode();
        this.success = success;
    }

    public JsonResult(BuzCode buzCode,boolean success){
        this.result = null;
        this.message = buzCode.getMessage();
        this.code = buzCode.getCode();
        this.success  = success;
    }

    public JsonResult(ResultCode result,boolean success){
        this.result = null;
        this.message = result.getMessage();
        this.code = result.getCode();
        this.success = success;
    }
    public static JsonResult Fail(){

        return new JsonResult(false);
    }
    public static JsonResult Fail(String message){

        return new JsonResult(message,false);
    }
    public JsonResult(boolean success){
        this.success = success;
        this.message = BuzCode.Fail.getMessage();
    }
    public static JsonResult Fail(BuzCode buzCode){

        return new JsonResult(buzCode,false);
    }
    public static JsonResult Fail(ResultCode message){

        return new JsonResult(message,false);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
