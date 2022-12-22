package com.profitsoft.internship.lesson7_8.task1.model;

public class User {

    private final String login;
    private final String password;
    private final String name;

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

}
