package edu.khanh.ProductManagement.dto.request;

import edu.khanh.ProductManagement.entity.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2,max = 100,message = "Tên sản phẩm phải từ 2-100 ký tự")
    private String name;

    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá không được nhỏ hơn 0")
    private Double price;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0,message = "Số lượng không được nhỏ hơn 0")
    private Integer quantity;

    @Size(max = 1000,message = "Tên nằm không quá 1000 ký tự")
    private String description;

    private boolean active;

    private Long categoryId;
}
