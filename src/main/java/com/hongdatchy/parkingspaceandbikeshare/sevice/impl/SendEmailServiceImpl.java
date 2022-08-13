package com.hongdatchy.parkingspaceandbikeshare.sevice.impl;


import com.hongdatchy.parkingspaceandbikeshare.entities.request.RegisterForm;
import com.hongdatchy.parkingspaceandbikeshare.sevice.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@EnableAutoConfiguration
public class SendEmailServiceImpl implements SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Override
    public boolean sendMail(String userMail, String header, String content) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(userMail);
            mail.setFrom(emailSender);
            mail.setSubject(header);
            mail.setText(content);
            javaMailSender.send(mail);
            return true;
        } catch (MailException ex) {
            return false;
        }
    }

    @Override
    public boolean sendMailHtml(String userMail, String header, String activeCode, RegisterForm registerForm) {
        try {
            System.out.println("sending email...");
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg =    "<h3>Chào mừng đến với hệ thống Bike Share!</h3>" +
                    "<p>Ai đó đã đănng kí tài khoản với email này. Thông tin tài khoản: </p>" +
                    "<p>username: " + registerForm.getEmail() + "</p>"+
                    "<p>password: " + registerForm.getPassword() + "</p>"+
                    "<p>Nếu đó là bạn, xin hãy nhập mã code này vào ứng dụng Bike Share để kích hoạt tài khoản: "
                    + activeCode +"</p>";
            helper.setText(htmlMsg, true);
            helper.setTo(userMail);
            helper.setSubject(header);
            helper.setFrom(emailSender);
            javaMailSender.send(mimeMessage);
            System.out.println("sending email success");
            return true;
        } catch (MailException | MessagingException ex) {
            System.out.println("sending email fail");
            return false;
        }
    }
}
