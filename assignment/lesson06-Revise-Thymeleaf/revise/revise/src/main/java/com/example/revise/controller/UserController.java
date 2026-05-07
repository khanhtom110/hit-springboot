package com.example.revise.controller;

import com.example.revise.dto.response.ApiResponse;
import com.example.revise.dto.response.CursorPageResponse;
import com.example.revise.dto.response.UserListResponse;
import com.example.revise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<CursorPageResponse<UserListResponse>>> findAll(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(ApiResponse.success(userService.findAllKey(cursor,size,search)));
    }
}
