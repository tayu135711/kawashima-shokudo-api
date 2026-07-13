package com.example.kawashimashokudoapi.repository;

import com.example.kawashimashokudoapi.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 1ユーザー1カートなので、ユーザーIDから自分のカートを取得するため
    Optional<Cart> findByUserId(Long userId);
}
