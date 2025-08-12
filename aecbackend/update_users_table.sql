-- Script cập nhật bảng users để thêm các cột audit fields
-- Chạy script này trong MySQL để sửa lỗi "Unknown column"

USE aecproject;

-- Thêm cột created_at
ALTER TABLE users ADD COLUMN created_at DATETIME DEFAULT CURRENT_TIMESTAMP;

-- Thêm cột created_by
ALTER TABLE users ADD COLUMN created_by VARCHAR(255) DEFAULT 'unknown';

-- Thêm cột updated_at
ALTER TABLE users ADD COLUMN updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP;

-- Thêm cột updated_by
ALTER TABLE users ADD COLUMN updated_by VARCHAR(255) DEFAULT 'unknown';

-- Thêm cột deleted_at
ALTER TABLE users ADD COLUMN deleted_at DATETIME NULL;

-- Thêm cột deleted_by
ALTER TABLE users ADD COLUMN deleted_by VARCHAR(255) DEFAULT 'unknown';

-- Cập nhật các bản ghi hiện tại
UPDATE users SET created_at = CURRENT_TIMESTAMP, created_by = 'SYSTEM' WHERE created_at IS NULL;

-- Tạo index để tối ưu hiệu suất
CREATE INDEX idx_users_deleted_at ON users(deleted_at);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_account ON users(account);

-- Hiển thị cấu trúc bảng sau khi cập nhật
DESCRIBE users;
