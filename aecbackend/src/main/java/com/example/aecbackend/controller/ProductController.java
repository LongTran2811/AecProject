package com.example.aecbackend.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

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
import com.example.aecbackend.payload.ApiResponse;
import com.example.aecbackend.payload.SoftDeleteRequest;
import com.example.aecbackend.service.ProductServ.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/products")
public class ProductController {
    

   public ProductController( ProductService productService){
    this.productService = productService;
   }
   private final ProductService productService;
    @GetMapping("/level")
    @Operation(summary = "Lấy tất cả sản phẩm có level")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByLevel() {
        List<ProductResponseDTO> products = productService.getProductsByLevel();
        products.forEach(product -> product.setDetail(null));
        return ResponseEntity.ok(products);
    }
    

    @GetMapping("/getId/{id}")
    @Operation(summary = "Lấy sản phẩm theo ID")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Tạo mới sản phẩm")
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.create(dto, "unknown")); // "admin" nên lấy từ JWT
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Cập nhật sản phẩm")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable String id, @Valid @RequestBody ProductRequestDTO dto, @RequestParam(value = "updatedBy", required = false) String updatedBy) {
        if (updatedBy == null) updatedBy = "unknown";
        return ResponseEntity.ok(productService.update(id, dto, updatedBy));
    }

    @DeleteMapping("/remove/{id}")
    @Operation(summary = "Xóa sản phẩm")
    public ResponseEntity<Void> delete(@PathVariable String id, @RequestParam(value = "deletedBy", required = false) String deletedBy) {
        if (deletedBy == null) deletedBy = "unknown";
        productService.delete(id, deletedBy);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Xóa nhiều sản phẩm")

    @PutMapping("removes")
    public ResponseEntity<?> softDeleteProducts(@RequestBody SoftDeleteRequest request) {
        String deletedBy = request.getDeletedBy() != null ? request.getDeletedBy() : "unknown";
        int deletedCount = productService.softDeleteProducts(request.getIds(), deletedBy);
        return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
            put("data", deletedCount);
            put("message", "Đã xóa thành công");
        }});
    }

    @GetMapping
    @Operation(summary = "Lấy tất cả sản phẩm ")
    public ResponseEntity<List<ProductResponseDTO>> getAllProductsHideDetail() {
        List<ProductResponseDTO> products = productService.getAll();
        products.forEach(product -> product.setDetail(null));
        return ResponseEntity.ok(products);
    }
}
