package edu.khanh.ProductManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;

    @Size(max = 500,message = "Mô tả không quá 500 ký tự")
    private String description;
}
