package com.example.aecbackend.controller;

import com.example.aecbackend.dto.UserDTO.CreateUserRequest;
import com.example.aecbackend.dto.UserDTO.LoginRequest;
import com.example.aecbackend.dto.UserDTO.LoginValidationResponse;
import com.example.aecbackend.dto.UserDTO.UserInfoResponse;
import com.example.aecbackend.payload.ApiResponse;
import com.example.aecbackend.service.UsereService.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập người dùng")
    public ResponseEntity<ApiResponse<UserInfoResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            ApiResponse<UserInfoResponse> response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(null, 400, e.getMessage()));
        }
    }

    @GetMapping("/validate")
    @Operation(summary = "Kiểm tra quyền đăng nhập")
    public ResponseEntity<ApiResponse<LoginValidationResponse>> validateLogin(
            @Parameter(description = "Tài khoản hoặc email") @RequestParam String accountOrEmail) {
        try {
            ApiResponse<LoginValidationResponse> response = authService.validateLogin(accountOrEmail);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(null, 400, e.getMessage()));
        }
    }
    
    @PostMapping("/create-user")
    @Operation(summary = "Tạo tài khoản với role tùy chọn (ADMIN hoặc USER)")
    public ResponseEntity<ApiResponse<String>> createUserWithRole(@Valid @RequestBody CreateUserRequest request) {
        try {
            ApiResponse<String> response = authService.createUserWithRole(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(null, 400, e.getMessage()));
        }
    }
}
