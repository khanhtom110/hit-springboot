package com.example.demolession02.controller;

import com.example.demolession02.dto.request.ProductCreationRequest;
import com.example.demolession02.dto.response.ApiResponse;
import com.example.demolession02.model.Product;
import com.example.demolession02.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(){
        return ResponseEntity
                .ok(ApiResponse.success(productService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProduct(@PathVariable int id){
        return ResponseEntity
                .ok(ApiResponse.success(productService.getById(id)));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Product>> create(@Valid @RequestBody ProductCreationRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(productService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> update(@PathVariable int id,
                                                       @Valid @RequestBody ProductCreationRequest request){
        return ResponseEntity
                .ok(ApiResponse.success(productService.updateProduct(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable int id){
        productService.delete(id);
        return ResponseEntity
                .ok(ApiResponse.success());
    }
}
