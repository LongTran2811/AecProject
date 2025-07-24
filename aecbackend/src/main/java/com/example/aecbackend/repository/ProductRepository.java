package com.example.aecbackend.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aecbackend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Additional query methods can be defined here if needed
    List<Product> findByIdIn(List<Integer> ids);
    List<Product> findAllByDeletedAtIsNull();
    List<Product> findByDeletedFalse();
    
} 
