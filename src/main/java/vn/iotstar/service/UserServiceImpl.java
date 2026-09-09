package vn.iotstar.service;

import java.util.Date;
import java.util.List;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.UserDao;
import vn.iotstar.entity.User;
import vn.iotstar.utils.MailUtil;
import vn.iotstar.utils.OtpUtil;
import vn.iotstar.utils.PasswordUtil;

public class UserServiceImpl implements IUserService {

    public IUserDao userDao = new UserDao();

    private static final java.util.regex.Pattern EMAIL_PATTERN =
            java.util.regex.Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    @Override
    public String register(String fullname, String email, String rawPassword) throws Exception {
        if (fullname == null || fullname.trim().length() < 2) {
            throw new Exception("Ho ten phai co it nhat 2 ky tu");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new Exception("Email khong dung dinh dang");
        }
        if (rawPassword == null || rawPassword.length() < 6) {
            throw new Exception("Mat khau phai co it nhat 6 ky tu");
        }

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

    @Override
    public User findById(int userId) {
        return userDao.findById(userId);
    }

    @Override
    public User updateProfile(int userId, String fullname, String phone, String avatarFileName) throws Exception {
        User user = userDao.findById(userId);
        if (user == null) {
            throw new Exception("Khong tim thay nguoi dung");
        }
        if (fullname == null || fullname.trim().length() < 2) {
            throw new Exception("Ho ten phai co it nhat 2 ky tu");
        }
        String trimmedPhone = phone == null ? null : phone.trim();
        if (trimmedPhone != null && !trimmedPhone.isEmpty() && !trimmedPhone.matches("^0[0-9]{9,10}$")) {
            throw new Exception("So dien thoai khong hop le (vi du: 0912345678)");
        }
        user.setFullname(fullname.trim());
        user.setPhone(trimmedPhone);
        if (avatarFileName != null && !avatarFileName.isEmpty()) {
            user.setAvatar(avatarFileName);
        }
        userDao.update(user);
        return user;
    }

    private Date addMinutes(Date date, int minutes) {
        return new Date(date.getTime() + minutes * 60 * 1000L);
    }

    // ==== CRUD danh cho trang quan tri (Admin) ====

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return userDao.findAll();
        }
        return userDao.searchByKeyword(keyword.trim());
    }

    @Override
    public void insert(User user) throws Exception {
        if (user.getFullname() == null || user.getFullname().trim().length() < 2) {
            throw new Exception("Ho ten phai co it nhat 2 ky tu");
        }
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail().trim()).matches()) {
            throw new Exception("Email khong dung dinh dang");
        }
        User existed = userDao.findByEmail(user.getEmail().trim());
        if (existed != null) {
            throw new Exception("Email da ton tai trong he thong");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new Exception("Mat khau khong duoc de trong");
        }

        user.setFullname(user.getFullname().trim());
        user.setEmail(user.getEmail().trim());
        user.setPassword(PasswordUtil.hash(user.getPassword()));
        userDao.insert(user);
    }

    @Override
    public void update(User user) throws Exception {
        if (user.getFullname() == null || user.getFullname().trim().length() < 2) {
            throw new Exception("Ho ten phai co it nhat 2 ky tu");
        }
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail().trim()).matches()) {
            throw new Exception("Email khong dung dinh dang");
        }

        User existing = userDao.findById(user.getUserId());
        if (existing == null) {
            throw new Exception("Nguoi dung khong ton tai");
        }

        User sameEmail = userDao.findByEmail(user.getEmail().trim());
        if (sameEmail != null && sameEmail.getUserId() != user.getUserId()) {
            throw new Exception("Email da duoc su dung boi tai khoan khac");
        }

        existing.setFullname(user.getFullname().trim());
        existing.setEmail(user.getEmail().trim());
        existing.setPhone(user.getPhone());
        existing.setRole(user.getRole());
        existing.setActive(user.getActive());
        // Neu admin nhap mat khau moi thi cap nhat, khong thi giu nguyen mat khau cu
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            existing.setPassword(PasswordUtil.hash(user.getPassword().trim()));
        }
        userDao.update(existing);
    }

    @Override
    public void delete(int id) throws Exception {
        userDao.delete(id);
    }

    @Override
    public int count() {
        return userDao.count();
    }
}
