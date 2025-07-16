package com.example.aecbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class CategoryRequestDTO {
    @NotBlank
    @Schema(description = "Tên danh mục",example = "Lpatop")
    private String title;

    @NotBlank
    @Schema(description = "URL hình ảnh",example = "https://example.com/image.jpg")
    private String image;

}
