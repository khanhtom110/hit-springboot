package com.example.week1;

import com.example.week1.notification.EmailNotification;
import com.example.week1.notification.SmsNotification;
import com.example.week1.payment.BankTransferPayment;
import com.example.week1.payment.CashPayment;
import com.example.week1.payment.MomoPayment;
import com.example.week1.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		PaymentMethod momoPayment=new MomoPayment();
		PaymentMethod cashPayment =new CashPayment();
		PaymentMethod bankTransferPayment =new BankTransferPayment();
		NotificationService smsNotification =new SmsNotification();
		NotificationService emailNotification =new EmailNotification();

		OrderService order1 =new OrderService(momoPayment, smsNotification);
		System.out.println("Order 1:");
		order1.processOrder("Nguyen Van A","Tu lanh",22000000);

		OrderService order2 =new OrderService(cashPayment, emailNotification);
		System.out.println("Order 2:");
		order2.processOrder("Nguyen Van B","Lo vi song",5000000);

		OrderService order3 =new OrderService(bankTransferPayment, smsNotification);
		System.out.println("Order 3:");
		order3.processOrder("Nguyen Van C","Ti vi",17000000);

		OrderService order4 =new OrderService(momoPayment, smsNotification);
		System.out.println("Order 4:");
		order4.processOrder("Nguyen Van D","IPhone 17 promax",32000000);

		OrderService order5 =new OrderService(momoPayment, emailNotification);
		System.out.println("Order 5:");
		order5.processOrder("Nguyen Van E","Laptop",28000000);

	}

}
