package com.example.fullflow.controller;

import com.example.fullflow.dto.request.CreateUserRequest;
import com.example.fullflow.dto.respone.ApiResponse;
import com.example.fullflow.model.User;
import com.example.fullflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        User user = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id,
                                                        @Valid @RequestBody CreateUserRequest request){
        return ResponseEntity.ok(ApiResponse.updated(userService.update(id,request)));
    }
}
