package com.example.aecbackend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "title", nullable = false, length = 30)
    private String title;
    @Column(name = "detail", nullable = false, columnDefinition = "TEXT")
    private String detail;
    @Column(name = "image", nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(name = "priceOriginal")
    private Integer priceOriginal;
    @Column(name = "priceOfficial")
    private Integer priceOfficial;
    @Column(name = "priceType", nullable = false, length = 20)
    private String priceType;
    private Integer status;
    private Integer priorityLevel;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category categoryId;

    public Product( Integer id, String title, String detail, String image, Integer priceOriginal, Integer priceOfficial, String priceType, Integer status, Integer priorityLevel, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, LocalDateTime deletedAt, String deletedBy, Category categoryId) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.image = image;
        this.priceOriginal = priceOriginal;
        this.priceOfficial = priceOfficial;
        this.priceType = priceType;
        this.status = status;
        this.priorityLevel = priorityLevel;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.categoryId = categoryId;
    }
    public Product( String title, String detail, String image, Integer priceOriginal, Integer priceOfficial, String priceType, Integer status, Integer priorityLevel, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, LocalDateTime deletedAt, String deletedBy, Category categoryId) {
        this.title = title;
        this.detail = detail;
        this.image = image;
        this.priceOriginal = priceOriginal;
        this.priceOfficial = priceOfficial;
        this.priceType = priceType;
        this.status = status;
        this.priorityLevel = priorityLevel;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.categoryId = categoryId;
    }
    public Product() {
    }
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
    public Integer getPriceOfficial() {
        return priceOfficial;
    }
    public void setPriceOfficial(Integer priceOfficial) {
        this.priceOfficial = priceOfficial;
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
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        this.updatedBy = updatedBy;
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
        this.deletedBy = deletedBy;
    }
    public Category getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }



}
