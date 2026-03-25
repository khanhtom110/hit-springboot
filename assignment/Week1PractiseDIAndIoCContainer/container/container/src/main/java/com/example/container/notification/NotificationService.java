package com.example.container.notification;

import org.springframework.stereotype.Component;

public interface NotificationService {
    void sendNotification(String to,String message);
}
