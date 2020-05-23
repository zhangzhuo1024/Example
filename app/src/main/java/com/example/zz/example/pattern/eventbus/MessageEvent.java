package com.example.zz.example.pattern.eventbus;

import com.example.zz.example.pattern.eventcenter.EventType;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/23
 */
public class MessageEvent {
    private EventType mEventType;

    public MessageEvent(EventType eventType) {
        this.mEventType = eventType;
    }

    public EventType getMessage(){
        return mEventType;
    }
}
