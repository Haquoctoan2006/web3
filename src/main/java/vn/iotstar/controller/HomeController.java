package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.constants.constants;
import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.ProductServiceImpl;

@WebServlet(urlPatterns = { "/home" })
public class HomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> latest = productService.findLatest(constants.LATEST_PRODUCT_COUNT);
        req.setAttribute("latestProducts", latest);
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}
