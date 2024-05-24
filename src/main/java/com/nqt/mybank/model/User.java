package com.nqt.mybank.model;

import java.util.Objects;

public class User {

    private String userId;

    private String name;

    public User(){}

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUserId());
    }
}
