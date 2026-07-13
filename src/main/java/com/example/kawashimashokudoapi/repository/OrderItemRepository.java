package com.example.kawashimashokudoapi.repository;

import com.example.kawashimashokudoapi.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 注文詳細画面向け:1つの注文に含まれる明細(メニュー内訳)を取得
    List<OrderItem> findByOrderId(Long orderId);
}
