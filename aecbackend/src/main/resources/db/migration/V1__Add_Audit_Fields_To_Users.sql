-- Thêm các cột audit fields vào bảng users
-- Chạy script này để cập nhật database hiện tại

-- Kiểm tra và thêm cột created_at
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS created_at DATETIME DEFAULT CURRENT_TIMESTAMP;

-- Kiểm tra và thêm cột created_by
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS created_by VARCHAR(255) DEFAULT 'unknown';

-- Kiểm tra và thêm cột updated_at
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP;

-- Kiểm tra và thêm cột updated_by
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS updated_by VARCHAR(255) DEFAULT 'unknown';

-- Kiểm tra và thêm cột deleted_at
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS deleted_at DATETIME NULL;

-- Kiểm tra và thêm cột deleted_by
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS deleted_by VARCHAR(255) DEFAULT 'unknown';

-- Cập nhật các bản ghi hiện tại có created_at = NULL
UPDATE users 
SET created_at = CURRENT_TIMESTAMP, 
    created_by = 'SYSTEM' 
WHERE created_at IS NULL;

-- Tạo index cho deleted_at để tối ưu hiệu suất query
CREATE INDEX IF NOT EXISTS idx_users_deleted_at ON users(deleted_at);

-- Tạo index cho email và account
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_account ON users(account);
