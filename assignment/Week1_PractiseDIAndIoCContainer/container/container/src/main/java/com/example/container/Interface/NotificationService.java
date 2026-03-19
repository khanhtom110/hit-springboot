package com.example.container.Interface;

import org.springframework.stereotype.Component;

@Component
public interface NotificationService {
    void sendNotification(String to,String message);
}
