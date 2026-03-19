package com.example.container.payment;

import com.example.container.Interface.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
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
