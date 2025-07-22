package com.example.aecbackend.service.ProductServ;

import com.example.aecbackend.dto.ProductDTO.ProductRequestDTO;
import com.example.aecbackend.dto.ProductDTO.ProductResponseDTO;
import java.util.List;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO dto, String createdBy);
    ProductResponseDTO update(int id, ProductRequestDTO dto, String updatedBy);
    void delete(Integer id, String deletedBy);
    List<ProductResponseDTO> getAll();
    Object getById(int id);
    
} 