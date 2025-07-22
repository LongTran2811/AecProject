package com.example.aecbackend.dto.ProductDTO;

import java.time.LocalDateTime;

public class ProductResponseDTO {
    private Integer id;
    private String title;
    private String detail;
    private String image;
    private Integer priceOriginal;
    private Integer priceOffcial;
    private String priceType;
    private Integer status;
    private Integer priorityLevel;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;
    private Integer categoryId;
    private String categoryTitle;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPriceOriginal() {
        return priceOriginal;
    }

    public void setPriceOriginal(Integer priceOriginal) {
        this.priceOriginal = priceOriginal;
    }

    public Integer getPriceOffcial() {
        return priceOffcial;
    }

    public void setPriceOffcial(Integer priceOffcial) {
        this.priceOffcial = priceOffcial;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy == null ? "" : createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? "" : createdBy.trim();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getUpdatedBy() {
        return updatedBy == null ? "" : updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? "" : updatedBy.trim();
    }
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
    public String getDeletedBy() {
        return deletedBy == null ? "" : deletedBy;
    }
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy == null ? "" : deletedBy.trim();
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryTitle() { return categoryTitle; }
    public void setCategoryTitle(String categoryTitle) { this.categoryTitle = categoryTitle; }

}
