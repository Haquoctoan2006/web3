package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.constants.constants;
import vn.iotstar.entity.Order;
import vn.iotstar.entity.User;
import vn.iotstar.service.IOrderService;
import vn.iotstar.service.OrderServiceImpl;

/**
 * Chuc nang mua hang cho khach hang:
 *   POST /order/buy   -> mua 1 san pham (yeu cau da dang nhap)
 *   GET  /orders      -> xem lich su don hang cua chinh minh
 */
@WebServlet(urlPatterns = { "/order/buy", "/orders" })
public class OrderController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public IOrderService orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = currentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Order> orders = orderService.findByUser(user.getUserId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/order/order-history.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = currentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int productId = Integer.parseInt(req.getParameter("productId"));
        int quantity = 1;
        try {
            quantity = Integer.parseInt(req.getParameter("quantity"));
        } catch (NumberFormatException ignored) {
        }

        try {
            orderService.buyProduct(user, productId, quantity);
            resp.sendRedirect(req.getContextPath() + "/orders");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            int id = productId;
            vn.iotstar.service.IProductService productService = new vn.iotstar.service.ProductServiceImpl();
            req.setAttribute("product", productService.findById(id));
            req.getRequestDispatcher("/views/product/product-detail.jsp").forward(req, resp);
        }
    }

    private User currentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute(constants.SESSION_USER);
    }
}
