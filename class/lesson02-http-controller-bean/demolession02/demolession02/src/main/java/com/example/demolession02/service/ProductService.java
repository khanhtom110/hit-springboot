package com.example.demolession02.service;

import com.example.demolession02.InitializeData;
import com.example.demolession02.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    List<Product> products=InitializeData.getProducts();


    public List<Product> findAll() {
        return InitializeData.getProducts();
    }
}
