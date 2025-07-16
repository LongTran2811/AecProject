package com.example.aecbackend.service;

import java.util.List;

import com.example.aecbackend.dto.CategoryRequestDTO;
import com.example.aecbackend.dto.CategoryResponseDTO;

public interface CategoryService {
    List<CategoryResponseDTO> getAll();
    CategoryResponseDTO getById(int id);
    CategoryResponseDTO create(CategoryRequestDTO dto , String createdBy);
    CategoryResponseDTO update(int id , CategoryRequestDTO dto, String updatedBy);
    void delete(int id , String deletedBy);
}
