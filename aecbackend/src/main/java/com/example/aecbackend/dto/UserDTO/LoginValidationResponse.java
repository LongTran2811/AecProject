package com.example.aecbackend.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginValidationResponse {
    private String accountOrEmail;
    private String roleName;
    private boolean requiresSecondPassword;
    private String message;
}
