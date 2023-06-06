package ru.itmo.wp.model.domain;

import java.util.Date;

public class Event implements DomainObject {
    private long id;
    private long userId;
    private EventType type;
    private Date creationTime;

    public enum EventType {
        ENTER,
        LOGOUT
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public EventType getType() {
        return type;
    }

    public Date getCreationTime() {
        return creationTime;
    }
}
