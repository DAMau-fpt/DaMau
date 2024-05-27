/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A utility class for sending e-mail messages
 *
 * @author duyplus
 */
public class QuenMKDAO {

    public static ResultSet rs = null; // Trả về kết quả truy vấn
    public static String SELECT_EMAIL_SQL = "SELECT * FROM NhanVien WHERE Email=?";
    public static String UPDATE_PASSWORD_SQL = "UPDATE NhanVien SET MatKhau=? WHERE Email=?";
    public String newPass = getRandomString(6);

    private String getRandomString(int n) {
        String txt = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+";
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            Random rd = new Random();
            sb.append(txt.charAt(rd.nextInt(txt.length())));
            n--;
        }
        return sb.toString();
    }

    private String message(String msg, String hoTen, String taiKhoan) {
        msg = "<div style='background-color:cyan;padding:15px;border-radius:10px;margin:0 auto;width:70%;line-height:25px;'>"
                + "Xin chào <b>" + hoTen + "</b>,<br/>"
                + "Chúng tôi muốn cho bạn biết rằng mật khẩu trên ứng dụng <b>EduSys</b> của bạn đã được đặt lại."
                + "<br/><br/>"
                + "Tên tài khoản: " + taiKhoan + "<br/>"
                + "Mật khẩu mới của bạn là: <b>" + newPass + "</b><br/>"
                + "Vui lòng truy cập vào tài khoản và tiến hành thay đổi mật khẩu ngay!"
                + "<br/><br/>"
                + "Nếu bạn gặp sự cố, vui lòng liên hệ với bộ phận hỗ trợ thông qua email: "
                + "<a href='mailto:duyplusdz@gmail.com'>duyplusdz@gmail.com</a><br/>"
                + "Vui lòng không trả lời email này bằng mật khẩu của bạn. "
                + "Chúng tôi sẽ không bao giờ hỏi mật khẩu của bạn và "
                + "chúng tôi đặc biệt không khuyến khích bạn chia sẻ mật khẩu đó với bất kỳ ai.</div>";
        return msg;
    }

    public void update(String key) {
        XJDBC.update(UPDATE_PASSWORD_SQL, newPass, key);
    }

    public void sendmail(String email, Object... args) {
        try {
            // Set default email account
            String fmail = "edusyslt@gmail.com";
            String fpass = "gavkl123";
            // Configure the SMTP Server properties
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            // Create a new session with an authenticator
            Session ss = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fmail, fpass);
                }
            });
            rs = XJDBC.query(SELECT_EMAIL_SQL, email);
            try {
                while (rs.next()) {
                    // Create a new email message
                    MimeMessage msg = new MimeMessage(ss);
                    msg.setFrom(new InternetAddress(fmail));
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rs.getString("Email")));
                    // Set subject, body message
                    String subject = "Mật khẩu của bạn đã được đặt lại - EduSys";
                    String body = message(email, rs.getString("MaNV"), rs.getString("HoTen"));
                    msg.setSubject(subject, "utf-8");
                    msg.setContent(body, "text/html;charset=utf-8");
                    // Send the email
                    Transport.send(msg);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException | MessagingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
