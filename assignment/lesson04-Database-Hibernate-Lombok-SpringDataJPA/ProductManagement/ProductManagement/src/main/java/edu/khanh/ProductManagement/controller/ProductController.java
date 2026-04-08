package edu.khanh.ProductManagement.controller;

import edu.khanh.ProductManagement.dto.request.CreateProductRequest;
import edu.khanh.ProductManagement.dto.request.UpdateProductRequest;
import edu.khanh.ProductManagement.dto.response.ApiResponse;
import edu.khanh.ProductManagement.entity.Product;
import edu.khanh.ProductManagement.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max
    ){
        List<Product> result;

        if(name!=null){
            result=productService.findByName(name);
        } else if (categoryId!=null) {
            result=productService.findByCategoryId(categoryId);
        } else if (min!=null && max!=null) {
            result=productService.findByPrice(min,max);
        }else {
            result=productService.findAll();
        }
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> findById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success(productService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> create(@Valid @RequestBody CreateProductRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(productService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> update(@PathVariable Long id,
                                                       @Valid @RequestBody UpdateProductRequest request){
        return ResponseEntity.ok(ApiResponse.success(productService.update(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
