package com.bus.result;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 11:36
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public enum  BuzCode {
    OK(200,"操作成功！"),
    Fail(300,"操作失败！"),
    param_is_null(400,"参数为空"),
    session_is_null(401,"session_is_null"),
    session_invalid(402,"session_invalid_error"),
    ;
    private String message;
    private Integer code;

     BuzCode(Integer code,String message){
        this.code = code;
        this.message = message;
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
}
