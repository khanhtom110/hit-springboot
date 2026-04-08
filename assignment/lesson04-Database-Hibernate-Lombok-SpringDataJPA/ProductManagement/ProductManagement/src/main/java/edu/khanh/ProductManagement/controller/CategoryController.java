package edu.khanh.ProductManagement.controller;

import edu.khanh.ProductManagement.dto.request.CreateCategoryRequest;
import edu.khanh.ProductManagement.dto.request.UpdateCategoryRequest;
import edu.khanh.ProductManagement.dto.response.ApiResponse;
import edu.khanh.ProductManagement.entity.Category;
import edu.khanh.ProductManagement.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> findAll(){
        return ResponseEntity.ok(ApiResponse.success(categoryService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> findById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success(categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> create(@Valid @RequestBody CreateCategoryRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(categoryService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> update(@PathVariable Long id,
                                                        @Valid @RequestBody UpdateCategoryRequest request){
        return ResponseEntity.ok(ApiResponse.success(categoryService.update(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }


}
