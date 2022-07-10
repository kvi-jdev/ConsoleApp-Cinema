package com.academy.entity.user;

import java.util.Objects;

public class UserLevel {

    private String userLevelName;

    public UserLevel(String userLevelName) {
        this.userLevelName = userLevelName;
    }

    @Override
    public String toString() {
        return userLevelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLevel userLevel = (UserLevel) o;
        return Objects.equals(userLevelName, userLevel.userLevelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userLevelName);
    }
}
