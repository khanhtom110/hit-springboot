package com.example.LibraryManagement.dto.request;

import com.example.LibraryManagement.enums.Category;
import com.example.LibraryManagement.enums.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(min = 2,max = 200,message = "Tiêu đề phải từ 2-200 ký tự")
    private String title;

    @NotBlank(message = "Mã sách không được để trống")
    private String isbn;

    @NotNull
    private Category category;

    @NotNull
    private Status status;

    @NotNull(message = "Tổng số khong duoc de trong")
    @Min(value = 0,message = "Số lượng không được nhỏ hơn 0")
    private Integer totalCopies;

    @NotNull(message = "So ban co san khong duoc de trong")
    @Min(value = 0,message = "Số lượng không được nhỏ hơn 0")
    private Integer availableCopies;

    @Max(value = 2026,message = "Nam sang tac phai trong qua khu")
    private  Integer publishedYear;

    @NotNull(message = "Ma tac gia khong duoc de trong")
    private Long authorId;
}
