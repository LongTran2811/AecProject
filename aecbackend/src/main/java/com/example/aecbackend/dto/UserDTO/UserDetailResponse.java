package com.example.aecbackend.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponse {
    private Integer id;
    private String fullname;
    private String email;
    private String emailAuthen;
    private String phone;
    private String account;
    private Integer role;
    
    // Audit fields
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;
    
    // Status field
    private String status; // "ACTIVE", "DELETED"
}
