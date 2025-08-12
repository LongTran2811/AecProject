package com.example.aecbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullname;
    private String email;
    private String emailAuthen;
    private String phone;
    private String account;
    private String password;
    private String secondPassword;
    private Integer role;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy = "unknown";
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "updated_by")
    private String updatedBy = "unknown";
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @Column(name = "deleted_by")
    private String deletedBy = "unknown";

    public User(Integer id, String fullname, String email, String emailAuthen, String phone, String account, String password, String secondPassword, Integer role, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, LocalDateTime deletedAt, String deletedBy) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.emailAuthen = emailAuthen;
        this.phone = phone;
        this.account = account;
        this.password = password;
        this.secondPassword = secondPassword;
        this.role = role;
        this.createdAt = createdAt;
        this.createdBy = createdBy != null ? createdBy : "unknown";
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy != null ? updatedBy : "unknown";
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy != null ? deletedBy : "unknown";
    }
    
    public User(String fullname, String email, String emailAuthen, String phone, String account, String password, String secondPassword, Integer role) {
        this.fullname = fullname;
        this.email = email;
        this.emailAuthen = emailAuthen;
        this.phone = phone;
        this.account = account;
        this.password = password;
        this.secondPassword = secondPassword;
        this.role = role;
        this.createdBy = "unknown";
        this.updatedBy = "unknown";
        this.deletedBy = "unknown";
    }
    
    public User() {
        // Default constructor
        this.createdBy = "unknown";
        this.updatedBy = "unknown";
        this.deletedBy = "unknown";
    }
    
    // Pre-persist method to set default values
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (createdBy == null || createdBy.isEmpty()) {
            createdBy = "unknown";
        }
    }
    
    // Pre-update method to set updated timestamp
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (updatedBy == null || updatedBy.isEmpty()) {
            updatedBy = "unknown";
        }
    }
    
    // Method to mark as deleted (soft delete)
    public void markAsDeleted(String deletedBy) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = deletedBy != null ? deletedBy : "unknown";
    }
    
    // Method to restore deleted record
    public void restore() {
        this.deletedAt = null;
        this.deletedBy = null;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmailAuthen() {
        return emailAuthen;
    }
    public void setEmailAuthen(String emailAuthen) {
        this.emailAuthen = emailAuthen;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSecondPassword() {
        return secondPassword;
    }
    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }
    public Integer getRole() {
        return role;
    }
    public void setRole(Integer role) {
        this.role = role;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy != null ? createdBy : "unknown";
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy != null ? updatedBy : "unknown";
    }
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
    public String getDeletedBy() {
        return deletedBy;
    }
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy != null ? deletedBy : "unknown";
    }
}
