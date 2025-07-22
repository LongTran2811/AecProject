package com.example.aecbackend.repository;
import com.example.aecbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Additional query methods can be defined here if needed

    
} 
