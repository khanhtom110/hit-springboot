package vn.edu.khanhtom.fullflow_revise.dto.request;

import jakarta.validation.constraints.*;

public class UserCreationRequest {

    @NotBlank(message = "Ten khong duoc bo trong")
    @Size(min = 2,max = 50,message = "Ten phai tu 2 den 50 ky tu")
    private String name;

    @NotBlank(message = "Email khong duoc bo trong")
    @Email(message = "Email khong dung dinh dang")
    private String email;

    @NotNull(message = "Tuoi khong duoc bo trong")
    @Min(value = 1,message = "Tuoi phai lon hon 1")
    @Max(value = 100,message = "Tuoi phai nho hon 100")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
