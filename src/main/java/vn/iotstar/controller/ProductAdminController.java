package vn.iotstar.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import vn.iotstar.constants.constants;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.CategoryServiceImpl;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.ProductServiceImpl;

/**
 * Trang quan tri (CRUD) cho Product, dung Servlet Multipart de upload anh
 * (theo huong dan "Upload file bang Multipart").
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = { "/admin/products", "/admin/product/add", "/admin/product/insert",
        "/admin/product/edit", "/admin/product/update", "/admin/product/delete" })
public class ProductAdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public IProductService productService = new ProductServiceImpl();
    public ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.contains("/admin/products")) {
            List<Product> list = productService.findAll();
            req.setAttribute("listproduct", list);
            req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);

        } else if (url.contains("/admin/product/add")) {
            req.setAttribute("listcate", categoryService.findAll());
            req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);

        } else if (url.contains("/admin/product/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productService.findById(id);
            req.setAttribute("product", product);
            req.setAttribute("listcate", categoryService.findAll());
            req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);

        } else if (url.contains("/admin/product/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                productService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        String uploadPath = constants.PRODUCT_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdirs();

        if (url.contains("/admin/product/insert")) {
            String name = req.getParameter("productName");
            String description = req.getParameter("description");
            double importPrice = parseDoubleSafe(req.getParameter("importPrice"), 0);

            try {
                double price = parseDoubleStrict(req.getParameter("price"), "Gia ban khong hop le");
                int quantity = parseIntStrict(req.getParameter("quantity"), "So luong khong hop le");
                int categoryId = parseIntStrict(req.getParameter("categoryId"), "Vui long chon danh muc");
                int status = parseIntSafe(req.getParameter("status"), 1);

                validateProductInput(name, price, quantity, categoryId);

                Product product = new Product();
                product.setProductName(name.trim());
                product.setDescription(description);
                product.setPrice(price);
                product.setImportPrice(importPrice);
                product.setQuantity(quantity);
                product.setStatus(status);
                product.setCreatedDate(new Date());

                Category category = categoryService.findById(categoryId);
                if (category == null) {
                    throw new Exception("Danh muc khong ton tai");
                }
                product.setCategory(category);

                try {
                    Part part = req.getPart("image");
                    if (part != null && part.getSize() > 0) {
                        String fname = saveFile(part, uploadPath);
                        product.setImage(fname);
                    } else {
                        product.setImage(constants.DEFAULT_FILENAME);
                    }
                } catch (FileNotFoundException fne) {
                    fne.printStackTrace();
                }

                productService.insert(product);
                resp.sendRedirect(req.getContextPath() + "/admin/products");
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
                req.setAttribute("listcate", categoryService.findAll());
                req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
            }
            return;
        }

        if (url.contains("/admin/product/update")) {
            int id = Integer.parseInt(req.getParameter("productId"));
            String name = req.getParameter("productName");
            String description = req.getParameter("description");
            double importPrice = parseDoubleSafe(req.getParameter("importPrice"), 0);

            Product product = productService.findById(id);

            try {
                double price = parseDoubleStrict(req.getParameter("price"), "Gia ban khong hop le");
                int quantity = parseIntStrict(req.getParameter("quantity"), "So luong khong hop le");
                int categoryId = parseIntStrict(req.getParameter("categoryId"), "Vui long chon danh muc");
                int status = parseIntSafe(req.getParameter("status"), 1);

                validateProductInput(name, price, quantity, categoryId);

                Category category = categoryService.findById(categoryId);
                if (category == null) {
                    throw new Exception("Danh muc khong ton tai");
                }

                String oldImage = product.getImage();
                product.setProductName(name.trim());
                product.setDescription(description);
                product.setPrice(price);
                product.setImportPrice(importPrice);
                product.setQuantity(quantity);
                product.setStatus(status);
                product.setCategory(category);

                try {
                    Part part = req.getPart("image");
                    if (part != null && part.getSize() > 0) {
                        if (oldImage != null && !oldImage.equals(constants.DEFAULT_FILENAME)) {
                            deleteFile(uploadPath + File.separator + oldImage);
                        }
                        String fname = saveFile(part, uploadPath);
                        product.setImage(fname);
                    } else {
                        product.setImage(oldImage);
                    }
                } catch (FileNotFoundException fne) {
                    fne.printStackTrace();
                }

                productService.update(product);
                resp.sendRedirect(req.getContextPath() + "/admin/products");
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
                req.setAttribute("product", product);
                req.setAttribute("listcate", categoryService.findAll());
                req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
            }
        }
    }

    /** Kiem tra du lieu san pham phia server (backstop cho HTML5 validation phia client). */
    private void validateProductInput(String name, double price, int quantity, int categoryId) throws Exception {
        if (name == null || name.trim().length() < 2) {
            throw new Exception("Ten san pham phai co it nhat 2 ky tu");
        }
        if (price < 0) {
            throw new Exception("Gia ban khong duoc am");
        }
        if (quantity < 0) {
            throw new Exception("So luong khong duoc am");
        }
        if (categoryId <= 0) {
            throw new Exception("Vui long chon danh muc hop le");
        }
    }

    private double parseDoubleStrict(String value, String errorMessage) throws Exception {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            throw new Exception(errorMessage);
        }
    }

    private int parseIntStrict(String value, String errorMessage) throws Exception {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new Exception(errorMessage);
        }
    }

    private int parseIntSafe(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private double parseDoubleSafe(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /** Luu file Part vao thu muc upload, tra ve ten file da luu. */
    private String saveFile(Part part, String uploadPath) throws IOException {
        String filename = Paths.get(getFileName(part)).getFileName().toString();
        int index = filename.lastIndexOf(".");
        String ext = index >= 0 ? filename.substring(index + 1) : "dat";
        String fname = System.currentTimeMillis() + "." + ext;
        part.write(uploadPath + File.separator + fname);
        return fname;
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return constants.DEFAULT_FILENAME;
    }

    private void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
