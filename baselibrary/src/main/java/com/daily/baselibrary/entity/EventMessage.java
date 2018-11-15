package com.daily.baselibrary.entity;

/**
 * description:{@link org.greenrobot.eventbus.EventBus}发送消息管理类
 * author: dlx
 * date: 2018/5/11
 * version: 1.0
 */
public class EventMessage {

    private String action;
    private Object message;

    public EventMessage() {
    }

    public EventMessage(String action) {
        this.action = action;
    }

    public EventMessage(String action, Object message) {
        this.action = action;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "action='" + action + '\'' +
                ", message=" + message +
                '}';
    }
}
