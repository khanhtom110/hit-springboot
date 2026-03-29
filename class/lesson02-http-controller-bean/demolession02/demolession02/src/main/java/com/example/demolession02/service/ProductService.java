package com.example.demolession02.service;

import com.example.demolession02.dto.request.ProductCreationRequest;
import com.example.demolession02.exception.ResourceNotFoundException;
import com.example.demolession02.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final List<Product> products= new ArrayList<>();
    int idNext=1;

    public List<Product> findAll() {
        return products;
    }


    public Product getById(int id) {
        return products.stream()
                .filter(product -> product.getId()==id)
                .findFirst()
                .orElseThrow(() ->  new ResourceNotFoundException("Product","id",id));
    }

    public Product create(ProductCreationRequest request) {
        Product product=new Product();
        product.setId(idNext++);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        products.add(product);
        return product;
    }

    public int findById(int id) {
        for(int i=0;i<products.size();i++){
            if (products.get(i).getId()==id){
                return i;
            }
        }
        throw new ResourceNotFoundException("Product","id",id);
    }

    public Product updateProduct(int id, ProductCreationRequest request) {
        boolean isExists=products.stream()
                .anyMatch(product -> product.getId()==id);
        Product product=new Product();
        product.setId(idNext++);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        int index=findById(id);
        products.set(index,product);
        return product;
    }

    public void delete(int id) {
        int index=findById(id);
        products.remove(index);
    }
}
