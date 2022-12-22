package com.profitsoft.internship.lesson7_8.task1.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cleanSession(request.getSession());

        request.getRequestDispatcher("/login").forward(request, response);
    }

    private void cleanSession(HttpSession session) {
        session.removeAttribute("auth");
        session.removeAttribute("error");
        session.removeAttribute("username");
        session.removeAttribute("userList");
    }

}
