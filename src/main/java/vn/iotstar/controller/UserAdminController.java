package vn.iotstar.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import vn.iotstar.constants.constants;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.UserServiceImpl;

/**
 * Trang quan tri (CRUD) cho User, dung Servlet Multipart de upload avatar
 * (cung mo hinh voi CategoryController/ProductAdminController).
 */
@MultipartConfig()
@WebServlet(urlPatterns = { "/admin/users", "/admin/user/add", "/admin/user/insert",
        "/admin/user/edit", "/admin/user/update", "/admin/user/delete" })
public class UserAdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.contains("/admin/users")) {
            String keyword = req.getParameter("keyword");
            List<User> list;
            if (keyword != null && !keyword.trim().isEmpty()) {
                list = userService.searchByKeyword(keyword);
            } else {
                list = userService.findAll();
            }
            req.setAttribute("listuser", list);
            req.setAttribute("keyword", keyword);
            req.getRequestDispatcher("/views/admin/user-list.jsp").forward(req, resp);

        } else if (url.contains("/admin/user/add")) {
            req.getRequestDispatcher("/views/admin/user-add.jsp").forward(req, resp);

        } else if (url.contains("/admin/user/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            User user = userService.findById(id);
            req.setAttribute("us", user);
            req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);

        } else if (url.contains("/admin/user/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                userService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        String uploadPath = constants.AVATAR_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdirs();

        if (url.contains("/admin/user/insert")) {
            User user = new User();
            user.setFullname(req.getParameter("fullname"));
            user.setEmail(req.getParameter("email"));
            user.setPassword(req.getParameter("password"));
            user.setPhone(req.getParameter("phone"));
            user.setRole(parseIntSafe(req.getParameter("role"), 0));
            user.setActive(parseIntSafe(req.getParameter("active"), 1));

            try {
                String fname = handleAvatarUpload(req, uploadPath, null);
                user.setAvatar(fname != null ? fname : "avatar.png");
            } catch (FileNotFoundException fne) {
                fne.printStackTrace();
            }

            try {
                userService.insert(user);
                resp.sendRedirect(req.getContextPath() + "/admin/users");
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
                req.setAttribute("us", user);
                req.getRequestDispatcher("/views/admin/user-add.jsp").forward(req, resp);
            }
            return;
        }

        if (url.contains("/admin/user/update")) {
            int userId = Integer.parseInt(req.getParameter("userId"));
            User existing = userService.findById(userId);
            String fileold = existing != null ? existing.getAvatar() : null;

            User user = new User();
            user.setUserId(userId);
            user.setFullname(req.getParameter("fullname"));
            user.setEmail(req.getParameter("email"));
            user.setPassword(req.getParameter("password")); // co the de trong = khong doi
            user.setPhone(req.getParameter("phone"));
            user.setRole(parseIntSafe(req.getParameter("role"), 0));
            user.setActive(parseIntSafe(req.getParameter("active"), 1));

            try {
                String fname = handleAvatarUpload(req, uploadPath, fileold);
                user.setAvatar(fname != null ? fname : fileold);
            } catch (FileNotFoundException fne) {
                fne.printStackTrace();
            }

            try {
                userService.update(user);
                resp.sendRedirect(req.getContextPath() + "/admin/users");
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
                req.setAttribute("us", user);
                req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);
            }
        }
    }

    /** Xu ly upload file avatar (neu co), tra ve ten file moi hoac null neu khong upload gi. */
    private String handleAvatarUpload(HttpServletRequest req, String uploadPath, String fileold)
            throws IOException, ServletException {
        Part part = req.getPart("avatar1");
        if (part != null && part.getSize() > 0) {
            if (fileold != null && !fileold.isEmpty() && !fileold.startsWith("https")) {
                deleteFile(uploadPath + "/" + fileold);
            }
            String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            int index = filename.lastIndexOf(".");
            String ext = filename.substring(index + 1);
            String fname = System.currentTimeMillis() + "." + ext;
            part.write(uploadPath + "/" + fname);
            return fname;
        }
        return null;
    }

    private int parseIntSafe(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
