package com.example.container;

import com.example.container.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ContainerApplication {

	public static void main(String[] args) {

		ApplicationContext context= SpringApplication.run(ContainerApplication.class, args);

		OrderService order1 =context.getBean(OrderService.class);
		System.out.println("Order 1:");
		order1.processOrder("Nguyen Van A","Tu lanh",22000000);

		OrderService order2 =context.getBean(OrderService.class);
		System.out.println("Order 2:");
		order2.processOrder("Nguyen Van B","Lo vi song",5000000);

		OrderService order3 =context.getBean(OrderService.class);
		System.out.println("Order 3:");
		order3.processOrder("Nguyen Van C","Ti vi",17000000);

		OrderService order4 =context.getBean(OrderService.class);
		System.out.println("Order 4:");
		order4.processOrder("Nguyen Van D","IPhone 17 promax",32000000);

		OrderService order5 =context.getBean(OrderService.class);
		System.out.println("Order 5:");
		order5.processOrder("Nguyen Van E","Laptop",28000000);

	}

}
