package com.example.aecbackend.dto.CategoryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
@Data
public class CategoryResponseDTO {
    @Schema(description = "ID của danh mục", example = "1")
    private int id;

    @Schema(description = "Tên danh mục", example = "laptop")
    private String title;

    @Schema(description = "Hình ảnh", example = "https://example.com/laptop.jpg")
    private String image;

    private LocalDateTime createdAt;
    private String createdBy;

    private LocalDateTime updatedAt;
    private String updatedBy;

    private LocalDateTime deletedAt;
    private String deletedBy;

    public String getTitle() {
        return title == null || title.trim().isEmpty() ? "unknown" : title;
    }
    public String getImage() {
        return image == null || image.trim().isEmpty() ? "unknown" : image;
    }
    public String getCreatedBy() {
        return createdBy == null || createdBy.trim().isEmpty() ? "unknown" : createdBy;
    }
    public String getUpdatedBy() {
        return updatedBy == null || updatedBy.trim().isEmpty() ? "unknown" : updatedBy;
    }
    public String getDeletedBy() {
        return deletedBy == null || deletedBy.trim().isEmpty() ? "unknown" : deletedBy;
    }
}
