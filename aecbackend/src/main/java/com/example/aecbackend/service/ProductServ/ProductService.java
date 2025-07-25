package com.example.aecbackend.service.ProductServ;

import com.example.aecbackend.dto.ProductDTO.ProductRequestDTO;
import com.example.aecbackend.dto.ProductDTO.ProductResponseDTO;
import java.util.List;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO dto, String createdBy);
    ProductResponseDTO update(String id, ProductRequestDTO dto, String updatedBy);
    void delete(String id, String deletedBy);
    List<ProductResponseDTO> getAll();
    ProductResponseDTO getById(String id);
    void softDeleteProducts(List<String> ids, String deletedBy);
    

    
} 