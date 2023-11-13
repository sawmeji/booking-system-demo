package com.mks.bookingsystemdemo.service;

import com.mks.bookingsystemdemo.entity.User;
import com.mks.bookingsystemdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public void registerUser(User user) {
        user.setVerified(false);
        userRepository.save(user);

        // Send verification email
        sendVerificationEmail(user);
    }

    private void sendVerificationEmail(User user) {
        String verificationToken = generateVerificationToken(user);
        String verificationLink = "http://your-app-url/verify?token=" + verificationToken;

        boolean emailSent = emailService.sendVerifyEmail(user.getEmail(), verificationLink);

        // Handle success and failure
        if (emailSent) {
            System.out.println("Email sent successfully.");
        } else {
            System.out.println("Failed to send email. Handle the failure appropriately.");
        }
    }

    private String generateVerificationToken(User user) {
        // Save this token in the database with the user ID
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.save(user);
        return token;
    }

    public void verifyUser(String token) {
        // Retrieve the user by token
        User user = userRepository.findByVerificationToken(token);

        // Mark the user as verified
        if (user != null) {
            user.setVerified(true);
            user.setVerificationToken(null); // Clear the verification token
            userRepository.save(user);
        }
    }
}
