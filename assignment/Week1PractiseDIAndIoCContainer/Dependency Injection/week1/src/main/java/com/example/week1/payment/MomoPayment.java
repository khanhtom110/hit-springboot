package com.example.week1.payment;

import com.example.week1.PaymentMethod;

public class MomoPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.printf("So tien phai tra: %,.2f dong%n",amount);
    }

    @Override
    public String getMethodName() {
        return "Thanh toan bang Momo";
    }
}
