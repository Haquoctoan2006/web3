package vn.iotstar.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import vn.iotstar.constants.constants;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.UserServiceImpl;

/**
 * Trang ho so ca nhan cua nguoi dung dang dang nhap:
 *   GET  /profile         -> xem/sua thong tin ca nhan
 *   POST /profile/update  -> cap nhat fullname, phone, anh dai dien (multipart)
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = { "/profile", "/profile/update" })
public class ProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = currentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        // Lay ban ghi moi nhat tu DB (phong khi session cu chua co phone/avatar)
        User fresh = userService.findById(user.getUserId());
        req.setAttribute("user", fresh);
        req.getRequestDispatcher("/views/account/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sessionUser = currentUser(req);
        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");

        String uploadPath = constants.AVATAR_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String newAvatarFileName = null;
        try {
            Part part = req.getPart("avatar");
            if (part != null && part.getSize() > 0) {
                String filename = Paths.get(getFileName(part)).getFileName().toString();
                int index = filename.lastIndexOf(".");
                String ext = index >= 0 ? filename.substring(index + 1) : "png";
                newAvatarFileName = "avatar_" + sessionUser.getUserId() + "_" + System.currentTimeMillis() + "." + ext;
                part.write(uploadPath + File.separator + newAvatarFileName);
            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        }

        try {
            User updated = userService.updateProfile(sessionUser.getUserId(), fullname, phone, newAvatarFileName);
            // Cap nhat lai session de nav (ten hien thi) va trang profile phan anh dung ngay
            HttpSession session = req.getSession(true);
            session.setAttribute(constants.SESSION_USER, updated);

            req.setAttribute("user", updated);
            req.setAttribute("message", "Cap nhat ho so thanh cong!");
        } catch (Exception e) {
            req.setAttribute("user", sessionUser);
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher("/views/account/profile.jsp").forward(req, resp);
    }

    private User currentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute(constants.SESSION_USER);
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return constants.DEFAULT_FILENAME;
    }
}
