package com.academy.entity.user;

import java.util.Objects;

public class User {

    private int id;

    private String login;

    private String password;

    private UserLevel level;

    public User() {
        this.level = Level.SIMPLE.get();
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.level = Level.SIMPLE.get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLevel getUserLevel() {
        return level;
    }

    public void setUserLevel(UserLevel level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                '}';
    }
}
