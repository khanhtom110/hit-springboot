package com.example.revise.dto.response;

import com.example.revise.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserDetailResponse {
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static UserDetailResponse from(User user){
        return UserDetailResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
