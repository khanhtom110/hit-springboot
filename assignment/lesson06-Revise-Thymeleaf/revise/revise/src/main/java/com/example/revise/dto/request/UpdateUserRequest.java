package com.example.revise.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UpdateUserRequest {
    @NotBlank(message = "Ten khong duoc de trong")
    @Size(min = 2,max = 50,message = "Ten phai tu 2-50 ky tu")
    private String name;

    @Email(message = "Email khong dung dinh dang")
    @NotBlank(message = "Email khong duoc de trong")
    private String email;

    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại phải có 10 chữ số và bắt đầu bằng 0")
    private String phoneNumber;
}
