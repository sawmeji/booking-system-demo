package com.mks.bookingsystemdemo.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public boolean sendVerifyEmail(String to, String profile) {
        // Simulate email sending
        System.out.println("Email sent to: " + to);
        System.out.println("Profile : " + profile);

        return true;
    }
}

