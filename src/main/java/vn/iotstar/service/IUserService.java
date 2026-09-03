package vn.iotstar.service;

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
}
