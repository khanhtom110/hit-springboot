package vn.edu.khanhtom.StudentManagement.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class CreateStudentRequest {

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Pattern(regexp = "^SV\\d{3,}$", message = "Mã sinh viên phải có định dạng SVxxx (Ví dụ: SV001)")
    private String studentCode;

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Email không được để trống")
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

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    private LocalDate dateOfBirth;

    @NotNull(message = "GPA không được để trống")
    @DecimalMin(value = "0.0", message = "GPA phải nằm trong khoảng từ 0.0 đến 4.0")
    @DecimalMax(value = "4.0", message = "GPA phải nằm trong khoảng từ 0.0 đến 4.0")
    private Double gpa;

    @NotBlank(message = "Ngành học không được để trống")
    private String major;

    @NotNull(message = "Năm học không được để trống")
    @Min(value = 1, message = "Năm học phải nằm trong khoảng từ 1 đến 6")
    @Max(value = 6, message = "Năm học phải nằm trong khoảng từ 1 đến 6")
    private Integer year;


    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}