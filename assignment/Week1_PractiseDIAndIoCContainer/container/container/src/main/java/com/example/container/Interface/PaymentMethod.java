package com.example.container.Interface;

import org.springframework.stereotype.Component;

@Component
public interface PaymentMethod {
    void pay(double amount);
    String getMethodName();
}
