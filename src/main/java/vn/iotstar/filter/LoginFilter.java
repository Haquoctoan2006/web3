package vn.iotstar.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.constants.constants;

/** Chan truy cap vao /admin/* neu chua dang nhap. */
@WebFilter("/admin/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        Object userObj = session != null ? session.getAttribute(constants.SESSION_USER) : null;

        if (userObj == null) {
            // Chua dang nhap
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        vn.iotstar.entity.User user = (vn.iotstar.entity.User) userObj;
        if (user.getRole() != 1) {
            // Da dang nhap nhung khong phai Admin -> khong cho vao khu vuc quan tri
            req.setAttribute("error", "Ban khong co quyen truy cap trang quan tri.");
            req.getRequestDispatcher("/views/home.jsp").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
