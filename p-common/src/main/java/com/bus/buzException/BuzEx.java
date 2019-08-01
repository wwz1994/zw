package com.bus.buzException;

import com.bus.result.BuzCode;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 11:42
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public class BuzEx extends RuntimeException {
    public BuzEx(String message){
        super(message);
    }
    public BuzEx(BuzCode buzCode){
        super(buzCode.getMessage());
        this.buzCode = buzCode;
    }
    private BuzCode buzCode;

    public BuzCode getBuzCode() {
        return buzCode;
    }

    public void setBuzCode(BuzCode buzCode) {
        this.buzCode = buzCode;
    }
}
