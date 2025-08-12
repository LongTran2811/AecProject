-- Script sửa lỗi "Unknown column" cho bảng users
-- Chạy script này trong MySQL để thêm các cột audit fields

USE aecproject;

-- Thêm các cột audit fields nếu chưa tồn tại
ALTER TABLE users ADD COLUMN IF NOT EXISTS created_at DATETIME DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE users ADD COLUMN IF NOT EXISTS created_by VARCHAR(255) DEFAULT 'unknown';
ALTER TABLE users ADD COLUMN IF NOT EXISTS updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE users ADD COLUMN IF NOT EXISTS updated_by VARCHAR(255) DEFAULT 'unknown';
ALTER TABLE users ADD COLUMN IF NOT EXISTS deleted_at DATETIME NULL;
ALTER TABLE users ADD COLUMN IF NOT EXISTS deleted_by VARCHAR(255) DEFAULT 'unknown';

-- Cập nhật các bản ghi hiện tại
UPDATE users SET created_at = CURRENT_TIMESTAMP, created_by = 'SYSTEM' WHERE created_at IS NULL;

-- Hiển thị cấu trúc bảng
DESCRIBE users;
