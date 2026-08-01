package com.group9.ems.service;

import com.group9.ems.entity.Event;
import com.group9.ems.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendConfirmation(User user, Event event) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(user.getEmail());
            msg.setSubject("Registration Confirmed: " + event.getTitle());
            msg.setText("Hello " + user.getName() + ",\n\n"
                    + "Your registration for \"" + event.getTitle() + "\" is confirmed.\n"
                    + "Date: " + event.getEventDate() + "\n"
                    + "Venue: " + event.getVenue() + "\n\n"
                    + "See you there!\nGroup 9 EMS");
            mailSender.send(msg);
        } catch (Exception e) {
            System.err.println("Email sending failed (non-fatal): " + e.getMessage());
        }
    }
}