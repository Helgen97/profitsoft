package com.profitsoft.internship.lesson7_8.task1.listeners;

import com.profitsoft.internship.lesson7_8.task1.model.User;
import com.profitsoft.internship.lesson7_8.task1.repository.UserRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ServerInitListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private final UserRepository userRepository = UserRepository.INSTANCE;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        userRepository.putUser(new User("admin", "admin", "Admin"));
        userRepository.putUser(new User("user1", "1234", "Andrey"));
        userRepository.putUser(new User("user2", "1234", "Misha"));
        userRepository.putUser(new User("user3", "1234", "Dmytro"));
        userRepository.putUser(new User("user4", "1234", "Katya"));
        userRepository.putUser(new User("user5", "1234", "Mila"));
    }

}
