package com.example.container.notification.impl;

import com.example.container.notification.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class EmailNotification implements NotificationService {

    @Override
    public void sendNotification(String to, String message) {
        System.out.println("Email: "+to);
        System.out.println(message);
    }
}
