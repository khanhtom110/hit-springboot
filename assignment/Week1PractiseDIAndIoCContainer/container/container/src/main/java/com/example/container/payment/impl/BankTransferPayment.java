package com.example.container.payment.impl;

import com.example.container.payment.PaymentMethod;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BankTransferPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.printf("So tien phai tra: %,.2f dong%n",amount);
    }

    @Override
    public String getMethodName() {
        return "Thanh toan qua ngan hang";
    }
}
