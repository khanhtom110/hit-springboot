package vn.edu.khanhtom.fullflow_revise.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.khanhtom.fullflow_revise.dto.request.UserCreationRequest;
import vn.edu.khanhtom.fullflow_revise.dto.respone.ApiResponse;
import vn.edu.khanhtom.fullflow_revise.model.User;
import vn.edu.khanhtom.fullflow_revise.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getUsers(){
        return ResponseEntity.ok(ApiResponse.success(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success(userService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody UserCreationRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(userService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id,
                                                        @Valid @RequestBody UserCreationRequest request){
        return ResponseEntity.ok(ApiResponse.success(userService.update(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
