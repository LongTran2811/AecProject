package com.example.aecbackend.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.aecbackend.dto.CategoryDTO.CategoryRequestDTO;
import com.example.aecbackend.dto.CategoryDTO.CategoryResponseDTO;
import com.example.aecbackend.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
     Category toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toResponseDTO(Category entity);

    List<CategoryResponseDTO> toResponseDTOs(List<Category> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CategoryRequestDTO dto, @MappingTarget Category category);
    
    
}
