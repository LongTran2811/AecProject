package com.example.aecbackend.dto.UserDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Tài khoản hoặc email không được để trống")
    private String accountOrEmail;
    
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
    
    // Mật khẩu cấp 2 chỉ bắt buộc cho ADMIN
    private String secondPassword;

    // Additional fields can be added as needed
}
