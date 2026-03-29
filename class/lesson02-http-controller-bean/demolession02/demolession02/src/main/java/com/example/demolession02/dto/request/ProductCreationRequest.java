package com.example.demolession02.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductCreationRequest {
    @NotBlank(message = "Ten khong duoc de trong")
    @Size(min = 2,max = 100,message = "Ten phai tu 2-100 ky tu")
    private String name;

    @NotNull(message = "Gia khong duoc de trong")
    @Positive(message = "Gia phai lon hon 0")
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
