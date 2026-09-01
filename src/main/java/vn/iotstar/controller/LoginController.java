package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.constants.constants;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.UserServiceImpl;

@WebServlet(urlPatterns = { "/login", "/logout" })
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        if (url.contains("/logout")) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.getRequestDispatcher("/views/account/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = userService.login(email, password);
            HttpSession session = req.getSession(true);
            session.setAttribute(constants.SESSION_USER, user);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/views/account/login.jsp").forward(req, resp);
        }
    }
}
