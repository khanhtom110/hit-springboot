package edu.khanh.ProductManagement.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2,max = 100,message = "Tên nằm trong khoảng 2-100 ký tự")
    private String name;

    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá không được nhỏ hơn 0")
    private Double price;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0,message = "Số lượng không được nhỏ hơn 0")
    private Integer quantity;

    @Size(max = 1000,message = "Tên nằm không quá 1000 ký tự")
    private String description;


}
