package edu.khanh.ProductManagement.dto.request;

import edu.khanh.ProductManagement.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @Size(max = 500,message = "Description không quá 500 ký tự")
    private String description;

}
