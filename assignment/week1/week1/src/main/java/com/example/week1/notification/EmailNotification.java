package com.example.week1.notification;

import com.example.week1.NotificationService;

public class EmailNotification implements NotificationService {
    @Override
    public void sendNotification(String to, String message) {
        System.out.println("Email: "+to);
        System.out.println(message);
    }
}
