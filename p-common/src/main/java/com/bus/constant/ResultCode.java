package com.bus.constant;

/**
 * Created by wwz on 2018-11-19.
 */
public enum ResultCode {
    pwd_error(104,"密码错误！"),
    username_error(101,"用户不存在！");
    private Integer code;
    private String message;
    ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
