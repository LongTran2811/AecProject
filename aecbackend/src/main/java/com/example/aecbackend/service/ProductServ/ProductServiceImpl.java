package com.example.aecbackend.service.ProductServ;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;
import com.example.aecbackend.dto.ProductDTO.ProductRequestDTO;
import com.example.aecbackend.dto.ProductDTO.ProductResponseDTO;
import com.example.aecbackend.entity.Category;
import com.example.aecbackend.entity.Product;
import com.example.aecbackend.mapper.ProductMapper;
import com.example.aecbackend.repository.CategoryRepository;
import com.example.aecbackend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductMapper productMapper;
    @Override
    @Transactional
    public ProductResponseDTO create(ProductRequestDTO dto, String createdBy) {
        Product product = productMapper.toEntity(dto);
        product.setId(generateRandomId());
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategoryId(category);
        product.setCreatedAt(LocalDateTime.now());
        product.setCreatedBy(createdBy);
        
        // Xử lý priorityLevel trước khi lưu
        handlePriorityLevel(product, dto.getPriorityLevel());
        
        return productMapper.toDTO(productRepo.save(product));
    }
    @Override
    @Transactional
    public ProductResponseDTO update (String id, ProductRequestDTO dto, String updatedBy) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        // Lưu priorityLevel cũ để so sánh
        Integer oldPriorityLevel = product.getPriorityLevel();
        
        // Cập nhật thông tin sản phẩm
        product.setTitle(dto.getTitle());
        product.setDetail(dto.getDetail());
        product.setImage(dto.getImage());
        product.setPriceOriginal(dto.getPriceOriginal());
        product.setPriceOfficial(dto.getPriceOfficial());
        product.setPriceType(dto.getPriceType());
        product.setStatus(dto.getStatus());
        product.setUpdatedAt(LocalDateTime.now());
        product.setUpdatedBy(updatedBy);
        product.setCategoryId(category);
        
        // Xử lý priorityLevel nếu có thay đổi
        if (!oldPriorityLevel.equals(dto.getPriorityLevel())) {
            // Trước tiên, xử lý như thể sản phẩm cũ bị xóa khỏi vị trí cũ
            handlePriorityLevelAfterDelete(product);
            
            // Sau đó, xử lý như thể thêm sản phẩm mới vào vị trí mới
            handlePriorityLevel(product, dto.getPriorityLevel());
        }
        
        return productMapper.toDTO(productRepo.save(product));
    }
    @Override
    @Transactional
    public void delete(String id, String deletedBy) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        // Lưu priorityLevel của sản phẩm bị xóa
        Integer deletedPriorityLevel = product.getPriorityLevel();
        
        product.setDeletedAt(LocalDateTime.now());
        product.setDeletedBy(deletedBy);
        productRepo.save(product);
        
        // Xử lý dịch chuyển các sản phẩm sau khi xóa
        handlePriorityLevelAfterDelete(product);
    }

    //     @Override
    // public List<ProductResponseDTO> getAll() {
    //     return productRepo.findAll().stream()
    //             .filter(product -> product.getDeletedAt() == null)
    //             .map(productMapper::toDTO)
    //             .toList();
    // }
        @Override
        public ProductResponseDTO getById(String id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDTO(product);
        }
    @Override
    public void softDeleteProducts(List<String> ids, String deletedBy) {
        List<Product> products = productRepo.findByIdIn(ids);
        LocalDateTime now = LocalDateTime.now();
        for (Product product : products) {
            product.setDeletedAt(now);
            product.setDeletedBy(deletedBy);
            product.setUpdatedAt(now);
        }
        productRepo.saveAll(products);
    }
    @Override
    public List<ProductResponseDTO> getAll() {
        return productRepo.findAllByDeletedAtIsNull()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getProductsByLevel() {
        return productRepo.findByPriorityLevelGreaterThanAndDeletedAtIsNullOrderByPriorityLevelAsc(0)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Thêm hàm sinh id random
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private String generateRandomId() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    /**
     * Xử lý logic priorityLevel khi thêm sản phẩm mới
     * Khi thêm sản phẩm vào level X:
     * - Các sản phẩm từ level X trở đi sẽ dồn lên 1 bậc (+1)
     * - Sản phẩm nào bị đẩy lên vượt level 10 thì chuyển về level 0 (không ưu tiên)
     */
    private void handlePriorityLevel(Product newProduct, Integer newPriorityLevel) {
        // Lấy tất cả sản phẩm có priorityLevel >= newPriorityLevel và chưa bị xóa
        List<Product> productsToShift = productRepo.findByPriorityLevelGreaterThanEqualAndDeletedAtIsNullOrderByPriorityLevelAsc(newPriorityLevel);
        
        // Dịch chuyển các sản phẩm có priorityLevel >= newPriorityLevel và < 10 lên 1 cấp
        for (Product product : productsToShift) {
            Integer currentLevel = product.getPriorityLevel();
            if (currentLevel >= newPriorityLevel && currentLevel < 10) {
                product.setPriorityLevel(currentLevel + 1);
                product.setUpdatedAt(LocalDateTime.now());
                productRepo.save(product);
            }
            // Nếu đã là 10 thì giữ nguyên
        }
        // Đặt priorityLevel cho sản phẩm mới
        newProduct.setPriorityLevel(newPriorityLevel);
    }

    /**
     * Xử lý logic priorityLevel sau khi xóa sản phẩm
     * Khi xóa sản phẩm ở level X:
     * - Sản phẩm bị xóa chuyển về level 0
     * - Các sản phẩm còn lại giữ nguyên
     */
    private void handlePriorityLevelAfterDelete(Product deletedProduct) {
        // Chỉ chuyển sản phẩm bị xóa về level 0
        if (deletedProduct != null) {
            deletedProduct.setPriorityLevel(0);
            deletedProduct.setUpdatedAt(LocalDateTime.now());
            productRepo.save(deletedProduct);
        }
    }
}
