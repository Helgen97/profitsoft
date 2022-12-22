package com.profitsoft.internship.lesson7_8.task1.servlets;

import com.profitsoft.internship.lesson7_8.task1.model.User;
import com.profitsoft.internship.lesson7_8.task1.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private final UserRepository userRepository = UserRepository.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (isRequestParametersEmpty(request, response, login, password)) return;

        processRequestDependingOnRequestCredential(request, response, login, password);

    }

    private void processRequestDependingOnRequestCredential(HttpServletRequest request, HttpServletResponse response, String login, String password) throws ServletException, IOException {
        User currentUser = userRepository.findUserByLogin(login);

        if (currentUser == null || !currentUser.getPassword().equals(password)) {
            request.setAttribute("error", "Wrong login or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("auth", true);
            request.getSession().setAttribute("username", currentUser.getName());
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
        }
    }

    private boolean isRequestParametersEmpty(HttpServletRequest request, HttpServletResponse response, String login, String password) throws ServletException, IOException {
        if (login.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Please fill empty fields!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return true;
        }
        return false;
    }
}
