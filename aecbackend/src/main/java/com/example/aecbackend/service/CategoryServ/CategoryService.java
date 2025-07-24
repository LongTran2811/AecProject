package com.example.aecbackend.service.CategoryServ;

import java.util.List;

import com.example.aecbackend.dto.CategoryDTO.CategoryRequestDTO;
import com.example.aecbackend.dto.CategoryDTO.CategoryResponseDTO;

public interface CategoryService {
    List<CategoryResponseDTO> getAll();
    CategoryResponseDTO getById(int id);
    CategoryResponseDTO create(CategoryRequestDTO dto , String createdBy);
    CategoryResponseDTO update(int id , CategoryRequestDTO dto, String updatedBy);
    void delete(int id , String deletedBy);
    void softDeleteMultiple(List<Integer> ids, String deletedBy);
}
