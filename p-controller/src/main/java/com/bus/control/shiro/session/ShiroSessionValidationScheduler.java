package com.bus.control.shiro.session;

import org.apache.shiro.session.mgt.SessionValidationScheduler;

/**
 * Created by wwz on 2018-11-21.
 */
public class ShiroSessionValidationScheduler implements SessionValidationScheduler {
    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void enableSessionValidation() {

    }

    @Override
    public void disableSessionValidation() {

    }
}