package com.example.aecbackend.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private Integer id;
    private String fullname;
    private String email;
    private String phone;
    private String account;
    private Integer role;
    private String roleName; // Thêm tên role
    private String token;
}
