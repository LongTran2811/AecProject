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

import com.example.aecbackend.dto.ProductDTO.ProductRequestDTO;
import com.example.aecbackend.dto.ProductDTO.ProductResponseDTO;
import com.example.aecbackend.entity.Product;
import com.example.aecbackend.service.ProductServ.ProductService;

import com.example.aecbackend.dto.CategoryDTO.CategoryRequestDTO;
import com.example.aecbackend.dto.CategoryDTO.CategoryResponseDTO;
import com.example.aecbackend.service.CategoryServ.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/products")
public class ProductController {
    

   public ProductController( ProductService productService){
    this.productService = productService;
   }
   private final ProductService productService;
    @GetMapping
    @Operation(summary = "Lấy tất cả sản phẩm")
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("getId/{id}")
    @Operation(summary = "Lấy sản phẩm theo ID")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping("create")
    @Operation(summary = "Tạo mới sản phẩm")
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.create(dto, "unknown")); // "admin" nên lấy từ JWT
    }

    @PutMapping("update/{id}")
    @Operation(summary = "Cập nhật sản phẩm")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable int id, @Valid @RequestBody ProductRequestDTO dto, @RequestParam(value = "updatedBy", required = false) String updatedBy) {
        if (updatedBy == null) updatedBy = "unknown";
        return ResponseEntity.ok(productService.update(id, dto, updatedBy));
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Xóa mềm sản phẩm")
    public ResponseEntity<Void> delete(@PathVariable int id, @RequestParam(value = "deletedBy", required = false) String deletedBy) {
        if (deletedBy == null) deletedBy = "unknown";
        productService.delete(id, deletedBy);
        return ResponseEntity.noContent().build();
    }


}
