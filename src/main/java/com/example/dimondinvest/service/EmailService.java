package com.example.dimondinvest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendmail(String to ,String subject , String message, String from){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        try {
            javaMailSender.send(simpleMailMessage);
            System.out.println("Mail sent to: " + to);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "mail sucess";
    }

}
