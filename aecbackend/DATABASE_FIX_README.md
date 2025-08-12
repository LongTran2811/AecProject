# Hướng dẫn sửa lỗi Database

## Lỗi hiện tại
```
JDBC exception executing SQL [select u1_0.id,u1_0.account,u1_0.created_at,u1_0.created_by,u1_0.deleted_at,u1_0.deleted_by,u1_0.email,u1_0.emailAuthen,u1_0.fullname,u1_0.password,u1_0.phone,u1_0.role,u1_0.secondPassword,u1_0.updated_at,u1_0.updated_by from users u1_0 where (u1_0.account=? or u1_0.email=?) and u1_0.deleted_at is null] [Unknown column 'u1_0.created_at' in 'field list']
```

## Nguyên nhân
Lỗi này xảy ra vì database chưa có các cột audit fields (`created_at`, `updated_at`, `deleted_at`, `created_by`, `updated_by`, `deleted_by`) mà chúng ta đã thêm vào entity User.

## Cách sửa lỗi

### Phương pháp 1: Chạy script SQL (Khuyến nghị)

1. Mở MySQL Workbench hoặc MySQL Command Line Client
2. Kết nối đến database `aecproject`
3. Chạy script SQL trong file `fix_database.sql`:

```sql
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
```

### Phương pháp 2: Sử dụng DatabaseInitializer (Tự động)

1. Đảm bảo file `DatabaseInitializer.java` đã được tạo
2. Khởi động lại ứng dụng Spring Boot
3. Class này sẽ tự động kiểm tra và thêm các cột cần thiết

### Phương pháp 3: Tạo lại database

1. Xóa database hiện tại:
```sql
DROP DATABASE aecproject;
CREATE DATABASE aecproject;
```

2. Khởi động lại ứng dụng Spring Boot
3. Hibernate sẽ tự động tạo bảng với cấu trúc đúng

## Kiểm tra sau khi sửa

Sau khi chạy script, kiểm tra cấu trúc bảng:

```sql
DESCRIBE users;
```

Bảng `users` phải có các cột sau:
- `id` (INT, PRIMARY KEY)
- `fullname` (VARCHAR)
- `email` (VARCHAR)
- `emailAuthen` (VARCHAR)
- `phone` (VARCHAR)
- `account` (VARCHAR)
- `password` (VARCHAR)
- `secondPassword` (VARCHAR)
- `role` (INT)
- `created_at` (DATETIME)
- `created_by` (VARCHAR)
- `updated_at` (DATETIME)
- `updated_by` (VARCHAR)
- `deleted_at` (DATETIME)
- `deleted_by` (VARCHAR)

## Lưu ý

- Đảm bảo backup database trước khi thực hiện các thay đổi
- Nếu sử dụng phương pháp 1, chạy script SQL trước khi khởi động ứng dụng
- Sau khi sửa xong, khởi động lại ứng dụng Spring Boot
