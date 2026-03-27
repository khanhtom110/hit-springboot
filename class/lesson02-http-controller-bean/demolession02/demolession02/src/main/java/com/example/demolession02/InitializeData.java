package com.example.demolession02;

import com.example.demolession02.entity.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeData implements CommandLineRunner{

    public static List<Product> products=new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        Product product1=new Product(1,"product1",200000);
        Product product2=new Product(2,"product2",100000);
        Product product3=new Product(3,"product3",150000);
        Product product4=new Product(4,"product4",500000);
        Product product5=new Product(5,"product5",1000000);

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static void setProducts(List<Product> products) {
        InitializeData.products = products;
    }
}
