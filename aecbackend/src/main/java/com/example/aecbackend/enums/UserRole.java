package com.example.aecbackend.enums;

public enum UserRole {
    ADMIN(1, "ADMIN", "Quản trị viên"),
    USER(2, "USER", "Người dùng thường");
    
    private final Integer code;
    private final String name;
    private final String description;
    
    UserRole(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static UserRole fromCode(Integer code) {
        for (UserRole role : values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy role với code: " + code);
    }
    
    public static boolean isAdmin(Integer roleCode) {
        return ADMIN.getCode().equals(roleCode);
    }
    
    public static boolean requiresSecondPassword(Integer roleCode) {
        return isAdmin(roleCode);
    }
}
