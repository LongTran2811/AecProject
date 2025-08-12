package com.example.aecbackend.dto.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    
    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ tên phải từ 2 đến 100 ký tự")
    private String fullname;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    private String phone;
    
    @NotBlank(message = "Tài khoản không được để trống")
    @Size(min = 3, max = 50, message = "Tài khoản phải từ 3 đến 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Tài khoản chỉ được chứa chữ cái, số và dấu gạch dưới")
    private String account;
    
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 100, message = "Mật khẩu phải từ 6 đến 100 ký tự")
    private String password;
    
    @NotBlank(message = "Mật khẩu cấp 2 không được để trống")
    @Size(min = 6, max = 100, message = "Mật khẩu cấp 2 phải từ 6 đến 100 ký tự")
    private String secondPassword;
    
    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword;
    
    @NotBlank(message = "Xác nhận mật khẩu cấp 2 không được để trống")
    private String confirmSecondPassword;
    
    // Role: 1 = ADMIN, 2 = USER
    private Integer role = 2; // Mặc định là USER
}
