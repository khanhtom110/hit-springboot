package com.example.week1.service;

import com.example.week1.NotificationService;
import com.example.week1.PaymentMethod;

public class OrderService {
    private PaymentMethod paymentMethod;
    private NotificationService notificationService;

    public OrderService(PaymentMethod paymentMethod,NotificationService notificationService){
        this.paymentMethod=paymentMethod;
        this.notificationService=notificationService;
    }

    public void processOrder(String customer, String product, double amount){
        notificationService.sendNotification(customer,"Cam on quy khach da su dung dich vu cua cong ty ABC");
        paymentMethod.getMethodName();
        paymentMethod.pay(amount);
        System.out.println("Phuong thuc thanh toan: "+paymentMethod.getMethodName());
    }
}
