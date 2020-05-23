package com.example.zz.example.pattern.eventcenter;

import java.util.HashMap;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/23
 */
public class EventCenter {

    private final HashMap<EventType, EventListener> mHashMap;

    private EventCenter() {
        mHashMap = new HashMap<>();
    }

    public static EventCenter getInstance() {
        return EventCenterHolder.sInstance;
    }

    public void registerEvent(EventType eventType, EventListener eventListener) {
        if(mHashMap.containsKey(eventType)){

        }else {
            mHashMap.put(eventType, eventListener);
        }
    }

    public void unRegisterEvent(EventType eventType, EventListener eventListener){
        if(mHashMap.containsKey(eventType)){
            mHashMap.remove(eventType);
        }else {

        }
    }

    public void fireEvnet(EventType eventType) {
        if (mHashMap.containsKey(eventType)) {
            mHashMap.get(eventType).onEvent(eventType);
        }
    }

    public void fireEvnetMessage(EventMessage eventMessage) {

    }

    private static class EventCenterHolder {
        public static final EventCenter sInstance = new EventCenter();
    }
}
