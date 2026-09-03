package vn.iotstar.constants;

public class constants {
    // Absolute path on server where uploaded category images are stored.
    // Change this to a real writable folder on your machine / server.
    public static final String DIR = "C:/uploads/categories";

    // Absolute path on server where uploaded product images are stored.
    public static final String PRODUCT_DIR = "C:/uploads/products";

    // Absolute path on server where uploaded user avatar images are stored.
    public static final String AVATAR_DIR = "C:/uploads/avatars";

    public static final String DEFAULT_FILENAME = "default.file";

    // ==== SMTP mail config (dung Gmail App Password, khong dung mat khau Gmail
    // thuong) ====
    public static final String MAIL_HOST = "smtp.gmail.com";
    public static final String MAIL_PORT = "587";
    public static final String MAIL_USERNAME = "daigianguoi@gmail.com";
    public static final String MAIL_PASSWORD = "oaqnowqwftigtnrs";

    // So san pham hien thi tren 1 trang /product
    public static final int PRODUCT_PAGE_SIZE = 6;

    // So san pham moi nhat hien thi tren trang chu
    public static final int LATEST_PRODUCT_COUNT = 10;

    // Session attribute key luu user dang dang nhap
    public static final String SESSION_USER = "SESSION_USER";
}
