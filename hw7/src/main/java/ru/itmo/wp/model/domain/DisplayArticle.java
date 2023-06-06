package ru.itmo.wp.model.domain;

import java.util.Date;

public class DisplayArticle {
    long id;
    String userLogin;
    String title;
    String text;
    Date creationTime;
    boolean hidden;

    public DisplayArticle(long id, String userLogin, String title, String text, Date creationTime, boolean hidden) {
        this.id = id;
        this.userLogin = userLogin;
        this.title = title;
        this.text = text;
        this.creationTime = creationTime;
        this.hidden = hidden;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
