package vn.iotstar.service;

import java.util.List;

import vn.iotstar.entity.User;

public interface IUserService {

    /** Dang ky tai khoan moi (chua kich hoat) va gui OTP qua email. */
    String register(String fullname, String email, String rawPassword) throws Exception;

    /** Xac thuc OTP khi dang ky, kich hoat tai khoan neu dung. */
    boolean verifyRegisterOtp(String email, String otp);

    /** Sinh lai OTP moi va gui qua email cho tai khoan CHUA kich hoat (nut "Gui lai OTP"). */
    void resendOtp(String email) throws Exception;

    /** Dang nhap: tra ve User neu email/password dung va tai khoan da kich hoat, nguoc lai null. */
    User login(String email, String rawPassword) throws Exception;

    /** Sinh OTP quen mat khau va gui qua email. */
    String forgotPassword(String email) throws Exception;

    /** Xac thuc OTP quen mat khau va doi mat khau moi. */
    boolean resetPassword(String email, String otp, String newPassword);

    User findByEmail(String email);

    User findById(int userId);

    /** Cap nhat ho ten, so dien thoai va (neu co) ten file anh dai dien moi. Tra ve User da cap nhat. */
    User updateProfile(int userId, String fullname, String phone, String avatarFileName) throws Exception;

    // ==== CRUD danh cho trang quan tri (Admin) ====

    /** Lay toan bo danh sach user, dung cho man hinh quan tri. */
    List<User> findAll();

    /** Tim kiem user theo ho ten hoac email (dung cho o tim kiem trong trang quan tri). */
    List<User> searchByKeyword(String keyword);

    /** Admin tao moi 1 tai khoan user (co the chi dinh role, active ngay). */
    void insert(User user) throws Exception;

    /** Admin cap nhat thong tin 1 tai khoan user (ho ten, email, sdt, role, trang thai...). */
    void update(User user) throws Exception;

    /** Admin xoa 1 tai khoan user. */
    void delete(int id) throws Exception;

    int count();
}
