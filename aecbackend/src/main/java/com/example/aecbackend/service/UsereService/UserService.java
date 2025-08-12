package com.example.aecbackend.service.UsereService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aecbackend.dto.UserDTO.UserDetailResponse;
import com.example.aecbackend.entity.User;
import com.example.aecbackend.payload.ApiResponse;
import com.example.aecbackend.repository.UerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UerRepository userRepository;
    
    /**
     * Chuyển đổi User entity thành UserDetailResponse
     */
    private UserDetailResponse convertToUserDetailResponse(User user) {
        UserDetailResponse response = new UserDetailResponse();
        response.setId(user.getId());
        response.setFullname(user.getFullname());
        response.setEmail(user.getEmail());
        response.setEmailAuthen(user.getEmailAuthen());
        response.setPhone(user.getPhone());
        response.setAccount(user.getAccount());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setCreatedBy(user.getCreatedBy());
        response.setUpdatedAt(user.getUpdatedAt());
        response.setUpdatedBy(user.getUpdatedBy());
        response.setDeletedAt(user.getDeletedAt());
        response.setDeletedBy(user.getDeletedBy());
        response.setStatus(user.getDeletedAt() != null ? "DELETED" : "ACTIVE");
        return response;
    }
    
    /**
     * Lấy danh sách tất cả user (không bao gồm user đã bị xóa)
     */
    public List<UserDetailResponse> getAllActiveUsers() {
        return userRepository.findAll().stream()
            .filter(user -> user.getDeletedAt() == null)
            .map(this::convertToUserDetailResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Lấy user theo ID (không bao gồm user đã bị xóa)
     */
    public Optional<UserDetailResponse> getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() && user.get().getDeletedAt() != null) {
            return Optional.empty(); // User đã bị xóa
        }
        return user.map(this::convertToUserDetailResponse);
    }
    
    /**
     * Cập nhật thông tin user
     */
    public ApiResponse<String> updateUser(Integer id, User updatedUser, String updatedBy) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            return new ApiResponse<>(null, 404, "Không tìm thấy user");
        }
        
        User user = existingUser.get();
        if (user.getDeletedAt() != null) {
            return new ApiResponse<>(null, 404, "User đã bị xóa");
        }
        
        // Cập nhật thông tin
        if (updatedUser.getFullname() != null) {
            user.setFullname(updatedUser.getFullname());
        }
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhone() != null) {
            user.setPhone(updatedUser.getPhone());
        }
        
        user.setUpdatedBy(updatedBy != null ? updatedBy : "unknown");
        
        userRepository.save(user);
        
        return new ApiResponse<>("Cập nhật thành công", 200, "Cập nhật thành công");
    }
    
    /**
     * Soft delete user
     */
    public ApiResponse<String> deleteUser(Integer id, String deletedBy) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new ApiResponse<>(null, 404, "Không tìm thấy user");
        }
        
        User userToDelete = user.get();
        if (userToDelete.getDeletedAt() != null) {
            return new ApiResponse<>(null, 400, "User đã bị xóa trước đó");
        }
        
        userToDelete.markAsDeleted(deletedBy);
        userRepository.save(userToDelete);
        
        return new ApiResponse<>("Xóa thành công", 200, "Xóa thành công");
    }
    
    /**
     * Restore user đã bị xóa
     */
    public ApiResponse<String> restoreUser(Integer id, String restoredBy) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new ApiResponse<>(null, 404, "Không tìm thấy user");
        }
        
        User userToRestore = user.get();
        if (userToRestore.getDeletedAt() == null) {
            return new ApiResponse<>(null, 400, "User chưa bị xóa");
        }
        
        userToRestore.restore();
        userToRestore.setUpdatedBy(restoredBy != null ? restoredBy : "unknown");
        userRepository.save(userToRestore);
        
        return new ApiResponse<>("Khôi phục thành công", 200, "Khôi phục thành công");
    }
    
    /**
     * Lấy danh sách user đã bị xóa
     */
    public List<UserDetailResponse> getDeletedUsers() {
        return userRepository.findAll().stream()
            .filter(user -> user.getDeletedAt() != null)
            .map(this::convertToUserDetailResponse)
            .collect(Collectors.toList());
    }
}
