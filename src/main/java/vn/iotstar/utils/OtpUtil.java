package vn.iotstar.utils;

import java.security.SecureRandom;

public class OtpUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    /** Sinh ma OTP 6 chu so. */
    public static String generateOtp() {
        int otp = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(otp);
    }

    /** So phut hieu luc cua OTP. */
    public static final int OTP_VALID_MINUTES = 5;
}
