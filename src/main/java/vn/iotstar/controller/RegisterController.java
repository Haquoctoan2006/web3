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

@WebServlet(urlPatterns = { "/register", "/verify-otp", "/resend-otp" })
public class RegisterController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        if (url.contains("/verify-otp")) {
            String email = req.getParameter("email");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/account/verify-otp.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/views/account/register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.contains("/register")) {
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            try {
                userService.register(fullname, email, password);
                resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + encode(email));
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
                req.getRequestDispatcher("/views/account/register.jsp").forward(req, resp);
            }
            return;
        }

        if (url.contains("/resend-otp")) {
            String email = req.getParameter("email");
            try {
                userService.resendOtp(email);
                req.setAttribute("email", email);
                req.setAttribute("message", "Da gui lai ma OTP moi, vui long kiem tra email.");
            } catch (Exception e) {
                req.setAttribute("email", email);
                req.setAttribute("error", e.getMessage());
            }
            req.getRequestDispatcher("/views/account/verify-otp.jsp").forward(req, resp);
            return;
        }

        if (url.contains("/verify-otp")) {
            String email = req.getParameter("email");
            String otp = req.getParameter("otp");

            boolean ok = userService.verifyRegisterOtp(email, otp);
            if (ok) {
                req.setAttribute("message", "Kich hoat tai khoan thanh cong! Vui long dang nhap.");
                req.getRequestDispatcher("/views/account/login.jsp").forward(req, resp);
            } else {
                req.setAttribute("email", email);
                req.setAttribute("error", "Ma OTP khong dung hoac da het han");
                req.getRequestDispatcher("/views/account/verify-otp.jsp").forward(req, resp);
            }
        }
    }

    /** Ma hoa email truoc khi dua vao query string, tranh ky tu '+' bi hieu nham thanh dau cach. */
    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }
}
