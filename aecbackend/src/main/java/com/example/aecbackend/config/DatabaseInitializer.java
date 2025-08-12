package com.example.aecbackend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("Kiểm tra và khởi tạo cấu trúc database...");
        
        try {
            // Kiểm tra xem bảng users có tồn tại không
            boolean tableExists = checkTableExists("users");
            
            if (tableExists) {
                log.info("Bảng users đã tồn tại, kiểm tra các cột audit fields...");
                checkAndAddAuditColumns();
            } else {
                log.info("Bảng users chưa tồn tại, sẽ được tạo tự động bởi Hibernate...");
            }
            
            log.info("Khởi tạo database hoàn tất!");
            
        } catch (Exception e) {
            log.error("Lỗi khi khởi tạo database: {}", e.getMessage());
            log.error("Chi tiết lỗi: ", e);
        }
    }
    
    private boolean checkTableExists(String tableName) {
        try {
            String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'aecproject' AND table_name = ?";
            int count = jdbcTemplate.queryForObject(sql, Integer.class, tableName);
            return count > 0;
        } catch (Exception e) {
            log.warn("Không thể kiểm tra bảng {}: {}", tableName, e.getMessage());
            return false;
        }
    }
    
    private void checkAndAddAuditColumns() {
        String[] auditColumns = {
            "created_at", "created_by", "updated_at", "updated_by", "deleted_at", "deleted_by"
        };
        
        for (String column : auditColumns) {
            try {
                if (!checkColumnExists("users", column)) {
                    log.info("Thêm cột {} vào bảng users...", column);
                    addColumn("users", column);
                } else {
                    log.info("Cột {} đã tồn tại trong bảng users", column);
                }
            } catch (Exception e) {
                log.warn("Không thể kiểm tra/thêm cột {}: {}", column, e.getMessage());
            }
        }
    }
    
    private boolean checkColumnExists(String tableName, String columnName) {
        try {
            String sql = "SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = 'aecproject' AND table_name = ? AND column_name = ?";
            int count = jdbcTemplate.queryForObject(sql, Integer.class, tableName, columnName);
            return count > 0;
        } catch (Exception e) {
            log.warn("Không thể kiểm tra cột {} trong bảng {}: {}", columnName, tableName, e.getMessage());
            return false;
        }
    }
    
    private void addColumn(String tableName, String columnName) {
        try {
            String sql = getAddColumnSQL(tableName, columnName);
            jdbcTemplate.execute(sql);
            log.info("Đã thêm cột {} vào bảng {}", columnName, tableName);
        } catch (Exception e) {
            log.error("Lỗi khi thêm cột {} vào bảng {}: {}", columnName, tableName, e.getMessage());
        }
    }
    
    private String getAddColumnSQL(String tableName, String columnName) {
        switch (columnName) {
            case "created_at":
                return "ALTER TABLE " + tableName + " ADD COLUMN created_at DATETIME DEFAULT CURRENT_TIMESTAMP";
            case "created_by":
                return "ALTER TABLE " + tableName + " ADD COLUMN created_by VARCHAR(255) DEFAULT 'unknown'";
            case "updated_at":
                return "ALTER TABLE " + tableName + " ADD COLUMN updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP";
            case "updated_by":
                return "ALTER TABLE " + tableName + " ADD COLUMN updated_by VARCHAR(255) DEFAULT 'unknown'";
            case "deleted_at":
                return "ALTER TABLE " + tableName + " ADD COLUMN deleted_at DATETIME NULL";
            case "deleted_by":
                return "ALTER TABLE " + tableName + " ADD COLUMN deleted_by VARCHAR(255) DEFAULT 'unknown'";
            default:
                throw new IllegalArgumentException("Không hỗ trợ thêm cột: " + columnName);
        }
    }
}
