package com.example.LibraryManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthorRequest {
    @NotNull(message = "Tên không được để trống")
    @Size(min = 2,max = 100,message = "Tên phải từ 2-100 ký tự")
    private String name;

    @NotNull(message = "Email không được để trống")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Email không đúng định dạng (Ví dụ: abc@gmail.com)"
    )
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(
            regexp = "^0\\d{9}$",
            message = "Số điện thoại phải có đúng 10 chữ số và bắt đầu bằng số 0"
    )
    private String phone;
}
