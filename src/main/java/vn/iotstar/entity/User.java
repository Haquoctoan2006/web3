package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;

    @Column(name = "fullname", columnDefinition = "VARCHAR(255)")
    private String fullname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    // 0 = chua kich hoat, 1 = da kich hoat
    @Column(name = "active")
    private Integer active;
    // 0 = khach hang, 1 = admin
    @Column(name = "role")
    private Integer role;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Column(name = "otp")
    private String otp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "otpExpiry")
    private Date otpExpiry;

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(Date otpExpiry) {
        this.otpExpiry = otpExpiry;
    }
}
