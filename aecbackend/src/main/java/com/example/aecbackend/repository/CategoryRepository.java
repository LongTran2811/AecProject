package com.example.aecbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aecbackend.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByTitleContainingIgnoreCase(String title);
    List<Category> findAllByIdIn(List<Integer> ids);    
}
