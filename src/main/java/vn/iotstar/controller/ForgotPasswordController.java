package vn.iotstar.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.service.IUserService;
import vn.iotstar.service.UserServiceImpl;

@WebServlet(urlPatterns = { "/forgot-password", "/reset-password" })
public class ForgotPasswordController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        if (url.contains("/reset-password")) {
            req.setAttribute("email", req.getParameter("email"));
            req.getRequestDispatcher("/views/account/reset-password.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/views/account/forgot-password.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.contains("/forgot-password")) {
            String email = req.getParameter("email");
            try {
                userService.forgotPassword(email);
                resp.sendRedirect(req.getContextPath() + "/reset-password?email=" + encode(email));
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
                req.getRequestDispatcher("/views/account/forgot-password.jsp").forward(req, resp);
            }
            return;
        }

        if (url.contains("/reset-password")) {
            String email = req.getParameter("email");
            String otp = req.getParameter("otp");
            String newPassword = req.getParameter("newPassword");

            // Nut "Gui lai OTP" tren trang reset-password se submit voi otp rong
            if (otp == null || otp.isEmpty()) {
                try {
                    userService.forgotPassword(email);
                    req.setAttribute("email", email);
                    req.setAttribute("message", "Da gui lai ma OTP moi, vui long kiem tra email.");
                } catch (Exception e) {
                    req.setAttribute("email", email);
                    req.setAttribute("error", e.getMessage());
                }
                req.getRequestDispatcher("/views/account/reset-password.jsp").forward(req, resp);
                return;
            }

            boolean ok = userService.resetPassword(email, otp, newPassword);
            if (ok) {
                req.setAttribute("message", "Doi mat khau thanh cong! Vui long dang nhap.");
                req.getRequestDispatcher("/views/account/login.jsp").forward(req, resp);
            } else {
                req.setAttribute("email", email);
                req.setAttribute("error", "Ma OTP khong dung hoac da het han");
                req.getRequestDispatcher("/views/account/reset-password.jsp").forward(req, resp);
            }
        }
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }
}
