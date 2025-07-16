package com.example.aecbackend.mapper;

import com.example.aecbackend.dto.CategoryRequestDTO;
import com.example.aecbackend.dto.CategoryResponseDTO;
import com.example.aecbackend.entity.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
     Category toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toResponseDTO(Category entity);

    List<CategoryResponseDTO> toResponseDTOs(List<Category> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CategoryRequestDTO dto, @MappingTarget Category category);

    
}
