package com.example.container;

import com.example.container.notification.impl.SmsNotification;
import com.example.container.payment.impl.CashPayment;
import com.example.container.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InitializeData implements CommandLineRunner {

    private ApplicationContext context;

    public InitializeData(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {

        OrderService order1 =context.getBean(OrderService.class);
        System.out.println("Order 1:");
        order1.processOrder("Nguyen Van A","Tu lanh",22000000);

        OrderService order2 =context.getBean(OrderService.class);
        order2.setPaymentMethod(context.getBean(CashPayment.class));
        order2.setNotificationService(context.getBean(SmsNotification.class));
        System.out.println("Order 2:");
        order2.processOrder("Nguyen Van B","Lo vi song",5000000);

    }
}
