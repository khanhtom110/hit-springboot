package com.example.week1;

public interface PaymentMethod {
    void pay(double amount);
    String getMethodName();
}
