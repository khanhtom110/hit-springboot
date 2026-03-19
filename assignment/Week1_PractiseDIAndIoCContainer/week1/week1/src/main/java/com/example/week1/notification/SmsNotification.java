package com.example.week1.notification;

import com.example.week1.NotificationService;

public class SmsNotification implements NotificationService {
    @Override
    public void sendNotification(String to, String message) {
        System.out.println("SMS: Than gui - "+to);
        System.out.println(message);
    }
}
