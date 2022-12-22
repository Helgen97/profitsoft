package com.profitsoft.internship.lesson7_8.task1.servlets;

import com.profitsoft.internship.lesson7_8.task1.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserListServlet", value = "/user_list")
public class UserListServlet extends HttpServlet {

    private final UserRepository userRepository = UserRepository.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("userList", userRepository.getAllUsers());
        request.getRequestDispatcher("userList.jsp").forward(request, response);
    }

}
