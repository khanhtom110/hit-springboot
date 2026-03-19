package com.example.container.notification;

import com.example.container.Interface.NotificationService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SmsNotification implements NotificationService {
    @Override
    public void sendNotification(String to, String message) {
        System.out.println("SMS: "+to);
        System.out.println(message);
    }
}
