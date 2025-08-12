package com.example.aecbackend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.aecbackend.entity.User;

@Repository
public interface UerRepository extends JpaRepository<User, Integer> {

    // Tìm user theo account hoặc email (cho đăng nhập)
    @Query("SELECT u FROM User u WHERE (u.account = :accountOrEmail OR u.email = :accountOrEmail) AND u.deletedAt IS NULL")
    Optional<User> findByAccountOrEmail(@Param("accountOrEmail") String accountOrEmail);

    // Kiểm tra email đã tồn tại chưa
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.deletedAt IS NULL")
    Optional<User> findByEmail(@Param("email") String email);

    // Kiểm tra account đã tồn tại chưa
    @Query("SELECT u FROM User u WHERE u.account = :account AND u.deletedAt IS NULL")
    Optional<User> findByAccount(@Param("account") String account);

    // Kiểm tra phone đã tồn tại chưa
    @Query("SELECT u FROM User u WHERE u.phone = :phone AND u.deletedAt IS NULL")
    Optional<User> findByPhone(@Param("phone") String phone);
}
