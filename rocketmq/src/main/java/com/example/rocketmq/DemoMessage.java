package com.example.rocketmq;


public class DemoMessage {
    public static final String TOPIC="topic1";
    private long id;
    private Object message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DemoMessage{" +
                "id=" + id +
                ", message=" + message +
                '}';
    }
}
