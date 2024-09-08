package com.pfe.Bank.service;

import com.pfe.Bank.model.User;
import com.pfe.Bank.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendAccountActivatedEmail(String to, String username) {
        String subject = "Activation de votre compte";
        String message = "Bonjour " + username + ",\n\n" +
                "Votre compte a été activé avec succès. Vous pouvez maintenant vous connecter.\n\n" +
                "Merci.";
        sendEmail(to, subject, message);
    }

}
