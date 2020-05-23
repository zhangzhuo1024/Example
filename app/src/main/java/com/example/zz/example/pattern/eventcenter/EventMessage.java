package com.example.zz.example.pattern.eventcenter;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/23
 */
public class EventMessage {
    private EventType mEventType;
    public EventMessage(EventType eventType) {
        mEventType = eventType;
    }

    public EventType getEventType(){
        return mEventType;
    }
}
