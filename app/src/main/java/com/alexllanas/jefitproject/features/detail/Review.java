package com.alexllanas.jefitproject.features.detail;

public class Review {
    public int id;
    public User user;
    public String content = "";

    public Review(int id, User user, String content) {
        this.id = id;
        this.user = user;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
