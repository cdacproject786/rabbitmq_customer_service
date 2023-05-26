package com.customer.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailingService {

    @Value("${mail.sender}")
    private String fromMail;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOrderPlacedEmail(String toMail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Order Placed Successfully!!!");
        helper.setTo(toMail);
        helper.setFrom(fromMail);

        boolean html = true;

        helper.setText("<h3>Hey There!!<h3> <br> " +
                "<h5><i>Your order is placed successfully.</i></h5> <br>" +
                "<h5><i>Hold on for a while your order gets delivered</i></h5>",html);

        javaMailSender.send(mimeMessage);
    }

    public void sendOtp(String toMail, long otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("OTP FOR CHANGING PASSWORD");
        helper.setTo(toMail);
        helper.setFrom(fromMail);

        boolean html = true;

        helper.setText("<h3>The OTP for password change is "+otp+" <h3> <br> " +
                "<h5><i>This Otp is valid for 2 minutes only</i></h5> <br>" +
                "<h5><i>kindly do not share otp or password with anyone..</i></h5>",html);

        javaMailSender.send(mimeMessage);
    }

    public void senOrderDispatchedMail(String toMail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("ORDER DISPATCHED");
        helper.setTo(toMail);
        helper.setFrom(fromMail);

        boolean html = true;

        helper.setText("<h3>Order is on the way</h3>"  +
                "<h5><i>Our Delivery hero is on the way with your order</i></h5> <br>" +
                "<h5><i>Team - Food delivery App</i></h5>",html);

        javaMailSender.send(mimeMessage);
    }

    public void orderDeliveredMail(String toMail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("ORDER DELIVERED");
        helper.setTo(toMail);
        helper.setFrom(fromMail);

        boolean html = true;

        helper.setText("<h3>Order Delivered Successfully</h3>"  +
                "<h5><i>Thanks for using our services.</i></h5> <br>" +
                "<h5><i>Please Rate us on the app</i></h5>",html);

        javaMailSender.send(mimeMessage);
    }
}
