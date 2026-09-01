package vn.iotstar.service;

import java.util.Date;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.UserDao;
import vn.iotstar.entity.User;
import vn.iotstar.utils.MailUtil;
import vn.iotstar.utils.OtpUtil;
import vn.iotstar.utils.PasswordUtil;

public class UserServiceImpl implements IUserService {

    public IUserDao userDao = new UserDao();

    @Override
    public String register(String fullname, String email, String rawPassword) throws Exception {
        User existed = userDao.findByEmail(email);

        String otp = OtpUtil.generateOtp();

        if (existed != null) {
            if (existed.getActive() == 1) {
                // Da kich hoat roi thi khong cho dang ky trung nua
                throw new Exception("Email da duoc dang ky");
            }
            // Tai khoan cu chua kich hoat (vi du go nham email, chua nhan duoc OTP...):
            // cho phep dang ky lai, cap nhat thong tin va gui OTP moi thay vi chan cung.
            existed.setFullname(fullname);
            existed.setPassword(PasswordUtil.hash(rawPassword));
            existed.setOtp(otp);
            existed.setOtpExpiry(addMinutes(new Date(), OtpUtil.OTP_VALID_MINUTES));
            userDao.update(existed);

            MailUtil.sendOtpMail(email, "[Web] Ma OTP kich hoat tai khoan", otp);
            return otp;
        }

        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(PasswordUtil.hash(rawPassword));
        user.setActive(0); // chua kich hoat
        user.setRole(0);   // dang ky qua /register luon la khach hang, khong tu cap quyen admin
        user.setOtp(otp);
        user.setOtpExpiry(addMinutes(new Date(), OtpUtil.OTP_VALID_MINUTES));

        userDao.insert(user);

        MailUtil.sendOtpMail(email, "[Web] Ma OTP kich hoat tai khoan", otp);
        return otp; // tra ve de log/test, khong nen hien thi ra giao dien that
    }

    @Override
    public void resendOtp(String email) throws Exception {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new Exception("Email chua duoc dang ky");
        }
        if (user.getActive() == 1) {
            throw new Exception("Tai khoan da duoc kich hoat, khong can gui lai OTP");
        }
        String otp = OtpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(addMinutes(new Date(), OtpUtil.OTP_VALID_MINUTES));
        userDao.update(user);

        MailUtil.sendOtpMail(email, "[Web] Ma OTP kich hoat tai khoan", otp);
    }

    @Override
    public boolean verifyRegisterOtp(String email, String otp) {
        User user = userDao.findByEmail(email);
        if (user == null || user.getOtp() == null) {
            return false;
        }
        if (!user.getOtp().equals(otp)) {
            return false;
        }
        if (user.getOtpExpiry() != null && user.getOtpExpiry().before(new Date())) {
            return false; // het han
        }
        user.setActive(1);
        user.setOtp(null);
        user.setOtpExpiry(null);
        userDao.update(user);
        return true;
    }

    @Override
    public User login(String email, String rawPassword) throws Exception {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new Exception("Email khong ton tai");
        }
        if (user.getActive() != 1) {
            throw new Exception("Tai khoan chua duoc kich hoat, vui long kiem tra email");
        }
        if (!PasswordUtil.matches(rawPassword, user.getPassword())) {
            throw new Exception("Mat khau khong dung");
        }
        return user;
    }

    @Override
    public String forgotPassword(String email) throws Exception {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new Exception("Email khong ton tai trong he thong");
        }
        String otp = OtpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(addMinutes(new Date(), OtpUtil.OTP_VALID_MINUTES));
        userDao.update(user);

        MailUtil.sendOtpMail(email, "[Web] Ma OTP dat lai mat khau", otp);
        return otp;
    }

    @Override
    public boolean resetPassword(String email, String otp, String newPassword) {
        User user = userDao.findByEmail(email);
        if (user == null || user.getOtp() == null) {
            return false;
        }
        if (!user.getOtp().equals(otp)) {
            return false;
        }
        if (user.getOtpExpiry() != null && user.getOtpExpiry().before(new Date())) {
            return false;
        }
        user.setPassword(PasswordUtil.hash(newPassword));
        user.setOtp(null);
        user.setOtpExpiry(null);
        userDao.update(user);
        return true;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    private Date addMinutes(Date date, int minutes) {
        return new Date(date.getTime() + minutes * 60 * 1000L);
    }
}
