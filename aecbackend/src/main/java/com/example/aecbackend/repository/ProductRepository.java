package com.example.aecbackend.repository;
import com.example.aecbackend.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    // Additional query methods can be defined here if needed
    List<Product> findByIdIn(List<String> ids);
    List<Product> findAllByDeletedAtIsNull();
    List<Product> findByDeletedFalse();
    
} 
