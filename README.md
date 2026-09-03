
---

## Bai tap 03 (bo sung): SiteMesh 3 + Validation + Profile

### 1. SiteMesh Decorator 3 + Template Bootstrap
- Dependency: `org.sitemesh:sitemesh:3.2.1` (ban tuong thich Jakarta EE / Servlet 6.0 / Tomcat 10).
- Filter dang ky trong `web.xml`: `org.sitemesh.config.ConfigurableSiteMeshFilter`, ap dung `/*`.
- Cau hinh mapping: `WEB-INF/sitemesh3.xml`
  - `/admin/*` -> `WEB-INF/decorators/admin.jsp` (co sidebar quan tri)
  - `/*` (con lai) -> `WEB-INF/decorators/main.jsp` (navbar Bootstrap chinh, dong bo dang nhap/dang xuat,
    link Quan tri chi hien voi Admin)
- Template dung Bootstrap 5 (CDN). Cac trang noi dung (`home.jsp`, `product-list.jsp`, `product-detail.jsp`,
  `order-history.jsp`, `admin/product-list.jsp`) da bo `<nav>` rieng le vi decorator da lo phan nay.

### 2. Validation cho cac chuc nang co FORM
- Tat ca form (dang ky, dang nhap, quen mat khau, xac thuc OTP, CRUD Category, CRUD Product, Profile)
  duoc them rang buoc HTML5: `required`, `minlength`/`maxlength`, `pattern`, `type=email`, `min=0`...
- Dung Bootstrap validation styles (`novalidate` + class `was-validated` qua JS) de hien thong bao loi
  ngay tren form truoc khi submit.
- Validate phia server van duoc giu (vi du kiem tra email trung, OTP het han, mat khau khong dung...)
  thong qua exception va hien thi lai bang `${error}`.

### 3. Chuc nang Profile (khach hang tu cap nhat ho so)
- `User` co them cot `phone`, `avatar` (ten file anh dai dien).
- `GET  /profile`        : xem/sua ho so ca nhan cua chinh minh (yeu cau dang nhap).
- `POST /profile/update` : cap nhat fullname, phone, upload anh dai dien moi bang Servlet Multipart
  (`ProfileController`, dung `@MultipartConfig`), luu anh vao `constants.AVATAR_DIR`.
- Anh dai dien hien thi qua servlet dung chung `/image?type=avatar&fname=...`.
- Sau khi cap nhat thanh cong, session `SESSION_USER` duoc lam moi de ten hien thi tren navbar cap nhat
  ngay lap tuc.
- Trang profile cung di qua decorator SiteMesh (Bootstrap) nhu cac trang khac.

### Cau hinh truoc khi chay bai 03
- Doi `AVATAR_DIR` trong `constants.java` thanh thu muc that de luu anh dai dien.
- Voi `hibernate.hbm2ddl.auto=update`, cot `phone` va `avatar` se tu duoc them vao bang `users` khi
  chay lan dau.
