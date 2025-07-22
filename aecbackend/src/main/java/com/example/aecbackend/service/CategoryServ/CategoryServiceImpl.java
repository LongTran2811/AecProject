package com.example.aecbackend.service.CategoryServ;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.aecbackend.dto.CategoryDTO.CategoryRequestDTO;
import com.example.aecbackend.dto.CategoryDTO.CategoryResponseDTO;
import com.example.aecbackend.entity.Category;
import com.example.aecbackend.mapper.CategoryMapper;
import com.example.aecbackend.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    

    @Override
    public List<CategoryResponseDTO> getAll() {
        List<Category> categories = repository.findAll();
        return mapper.toResponseDTOs(categories);
    }

    @Override
    public CategoryResponseDTO getById(int id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục có ID: " + id));
        return mapper.toResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto, String createdBy) {
        Category category = mapper.toEntity(dto);
        category.setCreatedAt(LocalDateTime.now());
        category.setCreatedBy(createdBy);
        return mapper.toResponseDTO(repository.save(category));
    }

    @Override
    public CategoryResponseDTO update(int id, CategoryRequestDTO dto, String updatedBy) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục có ID: " + id));
        mapper.updateEntityFromDTO(dto, category);
        category.setUpdatedAt(LocalDateTime.now());
        category.setUpdatedBy(updatedBy);
        return mapper.toResponseDTO(repository.save(category));
    }

    @Override
    public void delete(int id, String deletedBy) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục có ID: " + id));
        category.setDeletedAt(LocalDateTime.now());
        category.setDeletedBy(deletedBy);
        repository.save(category); // Soft delete
    }
}
