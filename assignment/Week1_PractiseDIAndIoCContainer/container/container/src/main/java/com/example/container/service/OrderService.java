package com.example.container.service;

import com.example.container.Interface.NotificationService;
import com.example.container.Interface.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    private PaymentMethod paymentMethod;
    private NotificationService notificationService;

    @Autowired
    public OrderService(PaymentMethod paymentMethod, NotificationService notificationService) {
        this.paymentMethod = paymentMethod;
        this.notificationService = notificationService;
    }
    public void processOrder(String customer, String product, double amount){
        notificationService.sendNotification(customer,"Cam on quy khach da su dung dich vu cua cong ty ABC");
        paymentMethod.getMethodName();
        paymentMethod.pay(amount);
        System.out.println("Phuong thuc thanh toan: "+paymentMethod.getMethodName());
    }
}
