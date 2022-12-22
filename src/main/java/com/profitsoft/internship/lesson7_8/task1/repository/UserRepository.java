package com.profitsoft.internship.lesson7_8.task1.repository;

import com.profitsoft.internship.lesson7_8.task1.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static final UserRepository INSTANCE = new UserRepository();
    public final List<User> users;

    private UserRepository() {
        users = new ArrayList<>();
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User findUserByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public void putUser(User user) {
        users.add(user);
    }
}
