package vn.iotstar.utils;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import vn.iotstar.constants.constants;

public class MailUtil {

    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", constants.MAIL_HOST);
        props.put("mail.smtp.port", constants.MAIL_PORT);

        return Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(constants.MAIL_USERNAME, constants.MAIL_PASSWORD);
            }
        });
    }

    /** Gui email dang text don gian, dung cho gui OTP kich hoat / quen mat khau. */
    public static void sendOtpMail(String toEmail, String subject, String otp) {
        try {
            Session session = getSession();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(constants.MAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText("Ma OTP cua ban la: " + otp + "\nMa co hieu luc trong "
                    + OtpUtil.OTP_VALID_MINUTES + " phut.\nVui long khong chia se ma nay cho bat ky ai.");
            Transport.send(message);
        } catch (MessagingException e) {
            // Khong lam sap ung dung neu gui mail loi, chi log ra console.
            e.printStackTrace();
        }
    }
}
