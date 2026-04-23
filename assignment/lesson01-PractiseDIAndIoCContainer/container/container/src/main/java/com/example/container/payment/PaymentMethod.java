package com.example.container.payment;

import org.springframework.stereotype.Component;

public interface PaymentMethod {
    void pay(double amount);
    String getMethodName();
}
