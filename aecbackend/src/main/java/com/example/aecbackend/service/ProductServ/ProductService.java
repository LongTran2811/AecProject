package com.example.aecbackend.service.ProductServ;

import java.util.List;

import com.example.aecbackend.dto.ProductDTO.ProductRequestDTO;
import com.example.aecbackend.dto.ProductDTO.ProductResponseDTO;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO dto, String createdBy);
    ProductResponseDTO update(int id, ProductRequestDTO dto, String updatedBy);
    void delete(Integer id, String deletedBy);
    List<ProductResponseDTO> getAll();
    Object getById(int id);
    void softDeleteProducts(List<Integer> ids, String deletedBy);
    
} 