package com.example.aecbackend.service.ProductServ;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;
import com.example.aecbackend.dto.ProductDTO.ProductRequestDTO;
import com.example.aecbackend.dto.ProductDTO.ProductResponseDTO;
import com.example.aecbackend.entity.Category;
import com.example.aecbackend.entity.Product;
import com.example.aecbackend.mapper.ProductMapper;
import com.example.aecbackend.repository.CategoryRepository;
import com.example.aecbackend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductMapper productMapper;
    @Override
    public ProductResponseDTO create(ProductRequestDTO dto, String createdBy) {
        Product product = productMapper.toEntity(dto);
        product.setId(generateRandomId());
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategoryId(category);
        product.setCreatedAt(LocalDateTime.now());
        product.setCreatedBy(createdBy);
        return productMapper.toDTO(productRepo.save(product));
    }
    @Override
    public ProductResponseDTO update (String id, ProductRequestDTO dto, String updatedBy) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
          product.setTitle(dto.getTitle());
        product.setDetail(dto.getDetail());
        product.setImage(dto.getImage());
        product.setPriceOriginal(dto.getPriceOriginal());
        product.setPriceOfficial(dto.getPriceOfficial());
        product.setPriceType(dto.getPriceType());
        product.setStatus(dto.getStatus());
        product.setPriorityLevel(dto.getPriorityLevel());
        product.setUpdatedAt(LocalDateTime.now());
        product.setUpdatedBy(updatedBy);
        product.setCategoryId(category);
        return productMapper.toDTO(productRepo.save(product));
    }
    @Override
    public void delete(String id, String deletedBy) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setDeletedAt(LocalDateTime.now());
        product.setDeletedBy(deletedBy);
        productRepo.save(product);
    }

    //     @Override
    // public List<ProductResponseDTO> getAll() {
    //     return productRepo.findAll().stream()
    //             .filter(product -> product.getDeletedAt() == null)
    //             .map(productMapper::toDTO)
    //             .toList();
    // }
        @Override
        public ProductResponseDTO getById(String id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDTO(product);
        }
    @Override
    public void softDeleteProducts(List<String> ids, String deletedBy) {
        List<Product> products = productRepo.findByIdIn(ids);
        LocalDateTime now = LocalDateTime.now();
        for (Product product : products) {
            product.setDeletedAt(now);
            product.setDeletedBy(deletedBy);
            product.setUpdatedAt(now);
        }
        productRepo.saveAll(products);
    }
    @Override
    public List<ProductResponseDTO> getAll() {
        return productRepo.findAllByDeletedAtIsNull()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Thêm hàm sinh id random
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private String generateRandomId() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
