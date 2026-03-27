package com.example.demolession02.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Product {
    private int id;

    @NotBlank(message = "Ten khong duoc de trong")
    @Size(min = 2, max = 50, message = "Tên phải từ 2 đến 50 ký tự")
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
