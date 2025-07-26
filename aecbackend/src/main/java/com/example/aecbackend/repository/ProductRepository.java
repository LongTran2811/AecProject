package com.example.aecbackend.repository;
import com.example.aecbackend.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    // Additional query methods can be defined here if needed
    List<Product> findByIdIn(List<String> ids);
    List<Product> findAllByDeletedAtIsNull();
    List<Product> findByDeletedFalse();
    
    // Method để lấy sản phẩm theo priorityLevel >= value và chưa bị xóa, sắp xếp theo priorityLevel tăng dần
    List<Product> findByPriorityLevelGreaterThanEqualAndDeletedAtIsNullOrderByPriorityLevelAsc(Integer priorityLevel);
    
    // Method để lấy sản phẩm theo priorityLevel > value và chưa bị xóa, sắp xếp theo priorityLevel tăng dần
    List<Product> findByPriorityLevelGreaterThanAndDeletedAtIsNullOrderByPriorityLevelAsc(Integer priorityLevel);
} 
