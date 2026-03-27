package com.example.demolession02.service;

import com.example.demolession02.InitializeData;
import com.example.demolession02.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    List<Product> products=InitializeData.getProducts();

    public List<Product> findAll() {
        return InitializeData.getProducts();
    }


    public Product getById(int id) {
        for(Product product:InitializeData.getProducts()){
            if(product.getId()==id){
                return product;
            }
        }
        return null;
    }

    public Product save(Product product) {
        InitializeData.getProducts().add(product);
        return product;
    }

    public boolean existsById(int id) {
        for(Product product:InitializeData.getProducts()){
            if (product.getId()==id){
                return true;
            }
        }
        return false;
    }

    public int findById(int id) {
        for(int i=0;i<InitializeData.getProducts().size();i++){
            if (InitializeData.getProducts().get(i).getId()==id){
                return i;
            }
        }
        return -1;
    }

    public Product updateProduct(int index, Product product) {
        InitializeData.getProducts().set(index,product);
        return InitializeData.getProducts().get(index);
    }

    public void delete(int id) {
        int index=findById(id);
        InitializeData.getProducts().remove(index);
    }
}
