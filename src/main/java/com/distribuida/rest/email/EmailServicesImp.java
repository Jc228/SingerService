package com.distribuida.rest.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServicesImp implements EmailServices{
    @Autowired
    public JavaMailSender emailSender;

    public void enviar(String to, String subject, StringBuilder text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text.toString());
            emailSender.send(message);
        } catch (MailException exception) {
            System.err.println(exception);;
        }
        System.out.println("Enviado...");
    }
}
