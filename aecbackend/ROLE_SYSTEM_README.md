# Hệ thống Phân quyền - Role System

## Tổng quan
Hệ thống phân quyền đã được cập nhật với logic mới:

- **Role 1** = ADMIN (Quản trị viên) - **Có mật khẩu cấp 2**
- **Role 2** = USER (Người dùng thường) - **Không có mật khẩu cấp 2**

## Chi tiết phân quyền

### ADMIN (Role = 1)
- **Mô tả**: Quản trị viên hệ thống
- **Mật khẩu cấp 2**: **Bắt buộc** khi đăng nhập
- **Quyền hạn**: Toàn quyền quản lý hệ thống
- **Đăng nhập**: Cần cả mật khẩu thường và mật khẩu cấp 2

### USER (Role = 2)
- **Mô tả**: Người dùng thường
- **Mật khẩu cấp 2**: **Không bắt buộc** khi đăng nhập
- **Quyền hạn**: Quyền hạn cơ bản
- **Đăng nhập**: Chỉ cần mật khẩu thường

## API Endpoints

### 1. Đăng nhập: `POST /api/auth/login`

#### ADMIN (Role = 1):
```json
{
  "accountOrEmail": "admin@example.com",
  "password": "password123",
  "secondPassword": "adminpass123"  // Bắt buộc
}
```

#### USER (Role = 2):
```json
{
  "accountOrEmail": "user@example.com",
  "password": "password123"
  // Không cần secondPassword
}
```

### 2. Kiểm tra quyền: `GET /api/auth/validate?accountOrEmail=user@example.com`

Response:
```json
{
  "data": {
    "accountOrEmail": "admin@example.com",
    "roleName": "ADMIN",
    "requiresSecondPassword": true,
    "message": "Tài khoản ADMIN (role = 1) yêu cầu mật khẩu cấp 2"
  }
}
```

### 3. Tạo user với role tùy chọn: `POST /api/auth/create-user`

#### Tạo ADMIN:
```json
{
  "fullname": "Admin User",
  "email": "admin@example.com",
  "phone": "0123456789",
  "account": "admin123",
  "password": "password123",
  "secondPassword": "adminpass123",
  "confirmPassword": "password123",
  "confirmSecondPassword": "adminpass123",
  "role": 1  // 1 = ADMIN
}
```

#### Tạo USER:
```json
{
  "fullname": "Nguyễn Văn A",
  "email": "user@example.com",
  "phone": "0123456789",
  "account": "user123",
  "password": "password123",
  "secondPassword": "secondpass123",
  "confirmPassword": "password123",
  "confirmSecondPassword": "secondpass123",
  "role": 2  // 2 = USER (mặc định)
}
```

## Logic xử lý

### Đăng nhập:
1. **Kiểm tra tài khoản tồn tại**
2. **Kiểm tra mật khẩu thường**
3. **Kiểm tra role và mật khẩu cấp 2**:
   - Role = 1 (ADMIN): Bắt buộc mật khẩu cấp 2
   - Role = 2 (USER): Không bắt buộc mật khẩu cấp 2

### Validation:
- **ADMIN**: Phải có mật khẩu cấp 2
- **USER**: Có thể có hoặc không có mật khẩu cấp 2

## Lưu ý quan trọng

1. **Tất cả tài khoản đều có mật khẩu cấp 2** trong database
2. **Chỉ ADMIN mới bắt buộc sử dụng mật khẩu cấp 2** khi đăng nhập
3. **USER có thể đăng nhập mà không cần mật khẩu cấp 2**
4. **Nếu USER cung cấp mật khẩu cấp 2, hệ thống sẽ kiểm tra tính đúng đắn**
5. **Chỉ có một endpoint để tạo user**: `/api/auth/create-user` với role tùy chọn

## Ví dụ sử dụng

### Tạo ADMIN:
```bash
curl -X POST "http://localhost:8080/api/auth/create-user" \
  -H "Content-Type: application/json" \
  -d '{
    "fullname": "Super Admin",
    "email": "admin@example.com",
    "phone": "0123456789",
    "account": "admin",
    "password": "admin123",
    "secondPassword": "adminpass123",
    "confirmPassword": "admin123",
    "confirmSecondPassword": "adminpass123",
    "role": 1
  }'
```

### Tạo USER:
```bash
curl -X POST "http://localhost:8080/api/auth/create-user" \
  -H "Content-Type: application/json" \
  -d '{
    "fullname": "Test User",
    "email": "user@example.com",
    "phone": "0123456789",
    "account": "user123",
    "password": "password123",
    "secondPassword": "secondpass123",
    "confirmPassword": "password123",
    "confirmSecondPassword": "secondpass123",
    "role": 2
  }'
```

### Đăng nhập ADMIN:
```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "accountOrEmail": "admin@example.com",
    "password": "admin123",
    "secondPassword": "adminpass123"
  }'
```

### Đăng nhập USER:
```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "accountOrEmail": "user@example.com",
    "password": "password123"
  }'
```
