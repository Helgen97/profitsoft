package com.profitsoft.internship.lesson7_8.task1.security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        if (request instanceof HttpServletRequest httpRequest &&
            response instanceof HttpServletResponse httpResponse) {

            if (isRequestForwarded(httpRequest, httpResponse)) return;
        }

        chain.doFilter(request, response);
    }

    private boolean isRequestForwarded(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        Boolean auth = (Boolean) httpRequest.getSession().getAttribute("auth");

        switch (httpRequest.getRequestURI()) {
            case "/login" -> {
                if (auth != null && auth) {
                    httpRequest.getRequestDispatcher("welcome.jsp").forward(httpRequest, httpResponse);
                    return true;
                }
            }
            case "/welcome", "/user_list" -> {
                if (auth == null || !auth) {
                    httpRequest.setAttribute("error", "Please sign in to view this page!");
                    httpRequest.getRequestDispatcher("login.jsp").forward(httpRequest, httpResponse);
                    return true;
                }
            }
            default -> {
                return false;
            }
        }

        return false;
    }
}
