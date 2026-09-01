package vn.iotstar.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.constants.constants;

@WebServlet("/image")
public class ImageController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname = req.getParameter("fname");
        String type = req.getParameter("type"); // "product" hoac mac dinh la category
        if (fname == null || fname.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String baseDir = "product".equals(type) ? constants.PRODUCT_DIR : constants.DIR;
        File file = new File(baseDir, fname);
        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resp.setContentType(getServletContext().getMimeType(fname));
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
    }
}
