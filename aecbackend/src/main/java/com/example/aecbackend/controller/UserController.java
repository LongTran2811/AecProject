package com.example.aecbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.aecbackend.dto.UserDTO.UserDetailResponse;
import com.example.aecbackend.entity.User;
import com.example.aecbackend.payload.ApiResponse;
import com.example.aecbackend.service.UsereService.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả user đang hoạt động")
    public ResponseEntity<List<UserDetailResponse>> getAllActiveUsers() {
        List<UserDetailResponse> users = userService.getAllActiveUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin user theo ID")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUserById(
            @Parameter(description = "ID của user") @PathVariable Integer id) {
        Optional<UserDetailResponse> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(user.get(), 200, "Thành công"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật thông tin user")
    public ResponseEntity<ApiResponse<String>> updateUser(
            @Parameter(description = "ID của user") @PathVariable Integer id,
            @RequestBody User updatedUser,
            @RequestParam(value = "updatedBy", defaultValue = "unknown") String updatedBy) {
        ApiResponse<String> response = userService.updateUser(id, updatedUser, updatedBy);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa user (soft delete)")
    public ResponseEntity<ApiResponse<String>> deleteUser(
            @Parameter(description = "ID của user") @PathVariable Integer id,
            @RequestParam(value = "deletedBy", defaultValue = "unknown") String deletedBy) {
        ApiResponse<String> response = userService.deleteUser(id, deletedBy);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
    }

    @PostMapping("/{id}/restore")
    @Operation(summary = "Khôi phục user đã bị xóa")
    public ResponseEntity<ApiResponse<String>> restoreUser(
            @Parameter(description = "ID của user") @PathVariable Integer id,
            @RequestParam(value = "restoredBy", defaultValue = "unknown") String restoredBy) {
        ApiResponse<String> response = userService.restoreUser(id, restoredBy);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
    }

    @GetMapping("/deleted")
    @Operation(summary = "Lấy danh sách user đã bị xóa")
    public ResponseEntity<List<UserDetailResponse>> getDeletedUsers() {
        List<UserDetailResponse> deletedUsers = userService.getDeletedUsers();
        return ResponseEntity.ok(deletedUsers);
    }
}
