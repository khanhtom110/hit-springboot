package com.example.container.service;

import com.example.container.notification.NotificationService;
import com.example.container.payment.PaymentMethod;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private PaymentMethod paymentMethod;
    private NotificationService notificationService;

    public OrderService(@Qualifier("momoPayment") PaymentMethod paymentMethod,@Qualifier("emailNotification") NotificationService notificationService) {
        this.paymentMethod = paymentMethod;
        this.notificationService = notificationService;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod){
        this.paymentMethod=paymentMethod;
    }
    public void setNotificationService(NotificationService notificationService){
        this.notificationService=notificationService;
    }

    public void processOrder(String customer, String product, double amount){
        notificationService.sendNotification(customer,"Cam on quy khach da su dung dich vu cua cong ty ABC");
        paymentMethod.getMethodName();
        paymentMethod.pay(amount);
        System.out.println("Phuong thuc thanh toan: "+paymentMethod.getMethodName());
    }
}
