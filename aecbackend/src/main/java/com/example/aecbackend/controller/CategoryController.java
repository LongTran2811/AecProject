package com.example.aecbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aecbackend.dto.CategoryDTO.CategoryRequestDTO;
import com.example.aecbackend.dto.CategoryDTO.CategoryResponseDTO;
import com.example.aecbackend.service.CategoryServ.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categories")

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Lấy tất cả danh mục")
    public ResponseEntity<?> getAll() {
        List<CategoryResponseDTO> data = categoryService.getAll();
        return ResponseEntity.ok(new com.example.aecbackend.payload.ApiResponse<>(data, 1, "Success"));
    }

    @GetMapping("getId/{id}")
    @Operation(summary = "Lấy danh mục theo ID")
    public ResponseEntity<?> getById(@PathVariable int id) {
        CategoryResponseDTO data = categoryService.getById(id);
        return ResponseEntity.ok(new com.example.aecbackend.payload.ApiResponse<>(data, 1, "Success"));
    }

    @PostMapping("create")
    @Operation(summary = "Tạo mới danh mục")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO data = categoryService.create(dto, "admin"); // "admin" nên lấy từ JWT
        return ResponseEntity.ok(new com.example.aecbackend.payload.ApiResponse<>(data, 1, "Created successfully"));
    }

    @PutMapping("update/{id}")
    @Operation(summary = "Cập nhật danh mục")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody CategoryRequestDTO dto, @RequestParam(value = "updatedBy", required = false) String updatedBy) {
        if (updatedBy == null) updatedBy = "unknown";
        CategoryResponseDTO data = categoryService.update(id, dto, updatedBy);
        return ResponseEntity.ok(new com.example.aecbackend.payload.ApiResponse<>(data, 1, "Updated successfully"));
    }

    @DeleteMapping("remove/{id}")
    @Operation(summary = "Xóa mềm danh mục")
    public ResponseEntity<Void> delete(@PathVariable int id, @RequestParam(value = "deletedBy", required = false) String deletedBy) {
        if (deletedBy == null) deletedBy = "unknown";
        categoryService.delete(id, deletedBy);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Xóa mềm nhiều danh mục")
    @PutMapping("removes")
    public ResponseEntity<?> softDeleteMultiple(@RequestBody List<Integer> ids) {
        categoryService.softDeleteMultiple(ids, "unknown");
        return ResponseEntity.ok(new com.example.aecbackend.payload.ApiResponse<>(ids.size(), 1, "Đã xoá thành công"));
    }
}
