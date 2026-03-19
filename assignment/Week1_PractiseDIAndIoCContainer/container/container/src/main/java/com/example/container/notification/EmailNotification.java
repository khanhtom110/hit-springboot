package com.example.container.notification;

import com.example.container.Interface.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class EmailNotification implements NotificationService {

    @Override
    public void sendNotification(String to, String message) {
        System.out.println("Email: "+to);
        System.out.println(message);
    }
}
