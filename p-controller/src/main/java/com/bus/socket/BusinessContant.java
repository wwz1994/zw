package com.bus.socket;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author wwz
 * @date 2019-07-24
 * @descrption:
 */
public class BusinessContant {
    public static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

}