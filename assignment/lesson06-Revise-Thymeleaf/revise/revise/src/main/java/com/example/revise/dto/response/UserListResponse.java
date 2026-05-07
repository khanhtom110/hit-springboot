package com.example.revise.dto.response;

import com.example.revise.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserListResponse {

    private Long id;

    private String name;

    private String email;

    private Boolean active;

    public static UserListResponse from(User user){
        return UserListResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .active(user.getActive())
                .build();
    }
}
