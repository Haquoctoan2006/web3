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

/**
 * Public product pages:
 *   GET /product                    -> danh sach san pham, phan trang 6sp/trang (?page=0,1,2,...)
 *   GET /product?keyword=...        -> tim kiem san pham theo ten (khong phan trang)
 *   GET /product/detail?id=..       -> chi tiet 1 san pham
 */
@WebServlet(urlPatterns = { "/product", "/product/detail" })
public class ProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.contains("/product/detail")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productService.findById(id);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/views/product/product-detail.jsp").forward(req, resp);
            return;
        }

        // /product -> danh sach (co the tim kiem theo ten, hoac phan trang neu khong tim kiem)
        String keyword = req.getParameter("keyword");

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Co tu khoa: tim kiem theo ten san pham, khong phan trang
            List<Product> list = productService.searchByName(keyword.trim());
            req.setAttribute("listproduct", list);
            req.setAttribute("keyword", keyword.trim());
            req.setAttribute("currentPage", 0);
            req.setAttribute("totalPages", 0);
            req.getRequestDispatcher("/views/product/product-list.jsp").forward(req, resp);
            return;
        }

        int page = 0;
        String pageParam = req.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 0) page = 0;
            } catch (NumberFormatException ignored) {
            }
        }

        int pageSize = constants.PRODUCT_PAGE_SIZE;
        List<Product> list = productService.findAll(page, pageSize);
        int totalPages = productService.totalPages(pageSize);

        req.setAttribute("listproduct", list);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/views/product/product-list.jsp").forward(req, resp);
    }
}