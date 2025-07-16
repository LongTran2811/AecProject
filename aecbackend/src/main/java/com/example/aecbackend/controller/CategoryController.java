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

import com.example.aecbackend.dto.CategoryRequestDTO;
import com.example.aecbackend.dto.CategoryResponseDTO;
import com.example.aecbackend.service.CategoryService;

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
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy danh mục theo ID")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Tạo mới danh mục")
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto, "admin")); // "admin" nên lấy từ JWT
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật danh mục")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable int id, @Valid @RequestBody CategoryRequestDTO dto, @RequestParam(value = "updatedBy", required = false) String updatedBy) {
        if (updatedBy == null) updatedBy = "unknown";
        return ResponseEntity.ok(categoryService.update(id, dto, updatedBy));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa mềm danh mục")
    public ResponseEntity<Void> delete(@PathVariable int id, @RequestParam(value = "deletedBy", required = false) String deletedBy) {
        if (deletedBy == null) deletedBy = "unknown";
        categoryService.delete(id, deletedBy);
        return ResponseEntity.noContent().build();
    }
}
