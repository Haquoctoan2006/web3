# Category CRUD with JPA (Jakarta EE 6.0 + Hibernate 7 / JPA 3.0)

Bai tap: CRUD Category su dung JPA + Servlet/JSP, theo noi dung
"CRUD category bang JPA" va "Cau hinh va test JPA" (UTExLMS).

## Cong nghe su dung
- Java 17
- Jakarta Servlet 6.0 / JSP / JSTL
- JPA 3.0 + Hibernate ORM 7.4.6.Final (hibernate-core, hibernate-validator)
- Jakarta Validation API 3.1.1
- MySQL (mysql-connector-j)
- Maven (packaging: war)

## Cau truc thu muc
```
src/main/java/vn/iotstar/
  entity/       Category.java, Video.java
  config/       JpaConfig.java, TestJpa.java (test JPA doc lap, khong can server)
  dao/          ICategoryDao.java, CategoryDao.java
  service/      ICategoryService.java, CategoryServiceImpl.java
  controller/   CategoryController.java (CRUD servlet), ImageController.java (hien anh upload)
  constants/    constants.java (thu muc luu anh upload)
src/main/resources/META-INF/persistence.xml
src/main/webapp/
  index.jsp
  views/admin/category-list.jsp
  views/admin/category-add.jsp
  views/admin/category-edit.jsp
  WEB-INF/web.xml
```

## Cau hinh truoc khi chay
1. Tao database MySQL ten `webst2` bang MySQL Workbench (hoac doi ten trong `persistence.xml`).
2. Mo `src/main/resources/META-INF/persistence.xml`, chinh lai:
   - `jakarta.persistence.jdbc.url`
   - `jakarta.persistence.jdbc.user`
   - `jakarta.persistence.jdbc.password`
   cho dung voi MySQL (Workbench) cua ban (mac dinh user root, port 3306).
3. Mo `src/main/java/vn/iotstar/constants/constants.java`, doi `DIR` thanh
   mot thu muc that tren may/server de luu anh upload.
4. `hibernate.hbm2ddl.auto=update` se tu tao bang `categories` va `videos`
   khi chay lan dau.

## Test rieng tang JPA (khong can Tomcat)
Chay class `vn.iotstar.config.TestJpa` (co ham `main`) de kiem tra ket noi
va insert thu 1 Category + 1 Video.

## Chay ung dung web
1. Import project vao Eclipse/IntelliJ nhu mot Maven Web Project, hoac:
   ```
   mvn clean package
   ```
   file `target/CategoryCRUD-JPA.war` sinh ra co the deploy vao Tomcat 10+
   (yeu cau Tomcat ho tro Jakarta EE 9+ / namespace `jakarta.*`).
2. Truy cap: `http://localhost:8080/CategoryCRUD-JPA/admin/categories`

## Chuc nang CRUD Category
- `GET  /admin/categories`       : danh sach category
- `GET  /admin/category/add`     : form them moi
- `POST /admin/category/insert`  : luu category moi (co upload anh)
- `GET  /admin/category/edit`    : form sua (theo `id`)
- `POST /admin/category/update`  : cap nhat category (co doi anh)
- `GET  /admin/category/delete`  : xoa category (theo `id`)

---

## Bai tap 02 (bo sung)

### 1-3. Dang ky + kich hoat OTP qua email / Dang nhap - Dang xuat / Quen mat khau OTP
- `GET/POST /register`         : dang ky tai khoan (mac dinh chua kich hoat), sinh OTP 6 so va gui qua email
- `GET/POST /verify-otp`       : nhap OTP de kich hoat tai khoan (OTP het han sau `OtpUtil.OTP_VALID_MINUTES` = 5 phut)
- `GET/POST /login`            : dang nhap (chi cho phep tai khoan da active), luu `User` vao session
- `GET  /logout`               : huy session, dang xuat
- `GET/POST /forgot-password`  : nhap email de nhan OTP dat lai mat khau
- `GET/POST /reset-password`   : nhap OTP + mat khau moi de doi mat khau

Cau hinh gui mail trong `vn.iotstar.constants.constants`:
```java
MAIL_HOST = "smtp.gmail.com"
MAIL_PORT = "587"
MAIL_USERNAME = "your-email@gmail.com"
MAIL_PASSWORD = "your-app-password"   // dung App Password cua Gmail, khong dung mat khau thuong
```
Mat khau duoc bam bang SHA-256 (`vn.iotstar.utils.PasswordUtil`) truoc khi luu DB.

`vn.iotstar.filter.LoginFilter` chan tat ca request toi `/admin/*`, bat buoc phai dang nhap
(session co `SESSION_USER`) moi vao duoc trang quan tri Category/Product.

### Phan quyen Admin / Khach hang
- Bang `users` co them cot `role`: `0` = Khach hang (mac dinh khi tu dang ky qua `/register`),
  `1` = Admin.
- `LoginFilter` kiem tra ca 2 dieu kien: da dang nhap VA `role == 1` moi cho vao `/admin/*`.
  Neu dang nhap nhung khong phai Admin, se bi tra ve trang chu kem thong bao loi.
- Trang `/register` KHONG cho tu chon role (luon la Khach hang) vi ly do bao mat. De tao
  tai khoan Admin, sau khi da dang ky + kich hoat OTP binh thuong, chay cau lenh SQL truc
  tiep tren MySQL Workbench:
  ```sql
  UPDATE users SET role = 1 WHERE email = 'admin@gmail.com';
  ```
- `home.jsp` chi hien thi link "Quan tri" khi `SESSION_USER.role == 1`.

### 4. Bang Products (1-n voi Category) + CRUD + trang chu + phan trang + chi tiet
- Entity `Product` (`vn.iotstar.entity.Product`) co `@ManyToOne` toi `Category`; `Category` co
  them `@OneToMany` `products`.
- CRUD quan tri (yeu cau dang nhap):
  - `GET  /admin/products`
  - `GET  /admin/product/add`, `POST /admin/product/insert`
  - `GET  /admin/product/edit`, `POST /admin/product/update`
  - `GET  /admin/product/delete`
  - Upload anh san pham dung `@MultipartConfig` + `Part` (theo dung huong dan Servlet Multipart),
    luu vao thu muc `constants.PRODUCT_DIR`.
- Trang chu `GET /home` (welcome file `index.jsp` redirect toi day): hien thi 10 san pham moi nhat
  (`constants.LATEST_PRODUCT_COUNT`), sap xep theo `createdDate` giam dan.
- `GET /product` : danh sach TAT CA san pham, phan trang `constants.PRODUCT_PAGE_SIZE` = 6 sp/trang,
  dieu huong bang `?page=0,1,2,...`.
- `GET /product/detail?id=..` : trang chi tiet 1 san pham, duoc lien ket tu ca trang chu va `/product`.
- Anh hien thi qua servlet dung chung `/image?type=product&fname=...` (hoac `type=category`
  cho anh danh muc), doc file tu `constants.PRODUCT_DIR` / `constants.DIR`.

### Cau hinh truoc khi chay bai 02
1. Doi `PRODUCT_DIR` trong `constants.java` thanh thu muc that de luu anh san pham.
2. Dien thong tin SMTP that (Gmail App Password) vao `MAIL_USERNAME` / `MAIL_PASSWORD`.
3. Voi `hibernate.hbm2ddl.auto=update`, bang `products` va `users` se tu duoc tao khi chay lan dau.

---

## Chuc nang Mua ban cho khach hang (bo sung)

### Gia mua / Gia ban
- `Product` co them cot `importPrice` (gia mua/gia von, chi hien trong trang Admin de tinh loi nhuan)
  ben canh `price` (gia ban, hien thi cho khach hang).
- Bang admin `/admin/products` hien them cot Gia mua, Gia ban, Loi nhuan/san pham (= gia ban - gia mua).

### Mua hang (khach hang)
- Entity moi: `Order` (don hang), `OrderDetail` (chi tiet don hang, luu lai gia ban tai thoi diem mua).
- `GET/POST /order/buy` : khach hang dang nhap, chon so luong tren trang chi tiet san pham, bam
  "Mua ngay" de tao don hang. He thong tu tru so luong ton kho, kiem tra du hang truoc khi cho mua.
- `GET /orders` : xem lich su cac don hang cua chinh minh (yeu cau dang nhap).
- Trang chi tiet san pham (`/product/detail?id=`) hien form mua hang neu con hang va da dang nhap;
  neu chua dang nhap se hien link dang nhap; neu het hang se bao "San pham da het hang".
