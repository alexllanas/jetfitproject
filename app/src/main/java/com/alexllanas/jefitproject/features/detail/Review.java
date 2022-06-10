package com.alexllanas.jefitproject.features.detail;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Review {
    public String id;
    public User user;
    @SerializedName("text")
    public String content = "";

    public Review(String id, User user, String content) {
        this.id = id;
        this.user = user;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id) && Objects.equals(user, review.user) && Objects.equals(content, review.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, content);
    }
}
