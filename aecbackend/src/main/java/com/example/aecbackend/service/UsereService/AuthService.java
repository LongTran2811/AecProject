package com.example.aecbackend.service.UsereService;

import com.example.aecbackend.dto.UserDTO.CreateUserRequest;
import com.example.aecbackend.dto.UserDTO.LoginRequest;
import com.example.aecbackend.dto.UserDTO.LoginValidationResponse;
import com.example.aecbackend.dto.UserDTO.UserInfoResponse;
import com.example.aecbackend.entity.User;
import com.example.aecbackend.enums.UserRole;
import com.example.aecbackend.payload.ApiResponse;
import com.example.aecbackend.repository.UerRepository;
import com.example.aecbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UerRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse<UserInfoResponse> login(LoginRequest request){
        // Tìm user theo account hoặc email
        User user = userRepository.findByAccountOrEmail(request.getAccountOrEmail())
            .orElseThrow(() -> new RuntimeException("Tài khoản hoặc email không tồn tại"));

        // Kiểm tra user có bị xóa không
        if (user.getDeletedAt() != null) {
            throw new RuntimeException("Tài khoản đã bị vô hiệu hóa");
        }

        // Kiểm tra mật khẩu thường
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        // Kiểm tra role và mật khẩu cấp 2
        UserRole userRole = UserRole.fromCode(user.getRole());
        
        if (UserRole.requiresSecondPassword(user.getRole())) {
            // Nếu là ADMIN (role = 1) thì bắt buộc phải có mật khẩu cấp 2
            if (request.getSecondPassword() == null || request.getSecondPassword().trim().isEmpty()) {
                throw new RuntimeException("Tài khoản ADMIN yêu cầu mật khẩu cấp 2");
            }
            
            if (!passwordEncoder.matches(request.getSecondPassword(), user.getSecondPassword())) {
                throw new RuntimeException("Mật khẩu cấp 2 không đúng");
            }
        } else {
            // Nếu là USER (role = 2) thì không cần mật khẩu cấp 2
            if (request.getSecondPassword() != null && !request.getSecondPassword().trim().isEmpty()) {
                // Nếu có cung cấp mật khẩu cấp 2 thì kiểm tra (tùy chọn)
                if (!passwordEncoder.matches(request.getSecondPassword(), user.getSecondPassword())) {
                    throw new RuntimeException("Mật khẩu cấp 2 không đúng");
                }
            }
        }

        // Tạo token (JWT)
        String token = JwtUtil.generateToken(user.getAccount(), user.getRole());

        // Tạo response với thông tin user
        UserInfoResponse userInfo = new UserInfoResponse();
        userInfo.setId(user.getId());
        userInfo.setFullname(user.getFullname());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhone(user.getPhone());
        userInfo.setAccount(user.getAccount());
        userInfo.setRole(user.getRole());
        userInfo.setRoleName(userRole.getName()); // Set tên role
        userInfo.setToken(token);

        return new ApiResponse<>(userInfo, 200, "Đăng nhập thành công với quyền " + userRole.getDescription());
    }
    
    /**
     * Tạo user với role tùy chọn (ADMIN hoặc USER)
     */
    public ApiResponse<String> createUserWithRole(CreateUserRequest request) {
        // Kiểm tra mật khẩu và xác nhận mật khẩu
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu và xác nhận mật khẩu không khớp");
        }
        
        if (!request.getSecondPassword().equals(request.getConfirmSecondPassword())) {
            throw new RuntimeException("Mật khẩu cấp 2 và xác nhận mật khẩu cấp 2 không khớp");
        }
        
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng");
        }
        
        // Kiểm tra account đã tồn tại chưa
        if (userRepository.findByAccount(request.getAccount()).isPresent()) {
            throw new RuntimeException("Tài khoản đã được sử dụng");
        }
        
        // Kiểm tra phone đã tồn tại chưa
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        }
        
        // Kiểm tra role hợp lệ
        UserRole userRole;
        try {
            userRole = UserRole.fromCode(request.getRole());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Role không hợp lệ. Chỉ chấp nhận: 1 (ADMIN) hoặc 2 (USER)");
        }
        
        // Tạo user mới
        User newUser = new User();
        newUser.setFullname(request.getFullname());
        newUser.setEmail(request.getEmail());
        newUser.setEmailAuthen(request.getEmail());
        newUser.setPhone(request.getPhone());
        newUser.setAccount(request.getAccount());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setSecondPassword(passwordEncoder.encode(request.getSecondPassword()));
        newUser.setRole(userRole.getCode());
        newUser.setCreatedBy("SYSTEM");
        
        userRepository.save(newUser);
        
        return new ApiResponse<>("Tạo tài khoản " + userRole.getName() + " thành công", 200, 
            "Tạo tài khoản " + userRole.getDescription() + " thành công");
    }

    /**
     * Kiểm tra quyền đăng nhập trước khi yêu cầu mật khẩu cấp 2
     */
    public ApiResponse<LoginValidationResponse> validateLogin(String accountOrEmail) {
        try {
            User user = userRepository.findByAccountOrEmail(accountOrEmail)
                .orElseThrow(() -> new RuntimeException("Tài khoản hoặc email không tồn tại"));

            // Kiểm tra user có bị xóa không
            if (user.getDeletedAt() != null) {
                throw new RuntimeException("Tài khoản đã bị vô hiệu hóa");
            }

            UserRole userRole = UserRole.fromCode(user.getRole());
            boolean requiresSecondPassword = UserRole.requiresSecondPassword(user.getRole());

            LoginValidationResponse validation = new LoginValidationResponse();
            validation.setAccountOrEmail(accountOrEmail);
            validation.setRoleName(userRole.getName());
            validation.setRequiresSecondPassword(requiresSecondPassword);
            validation.setMessage(requiresSecondPassword ? 
                "Tài khoản ADMIN (role = 1) yêu cầu mật khẩu cấp 2" : 
                "Tài khoản USER (role = 2) không yêu cầu mật khẩu cấp 2");

            return new ApiResponse<>(validation, 200, "Kiểm tra quyền thành công");
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
