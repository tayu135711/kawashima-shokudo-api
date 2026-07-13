package com.example.kawashimashokudoapi.repository;

import com.example.kawashimashokudoapi.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // カート画面表示用:カート内の全アイテムを取得
    List<CartItem> findByCartId(Long cartId);

    // 同じメニューを追加しようとした時、新規追加ではなく既存の数量を増やすため
    Optional<CartItem> findByCartIdAndMenuId(Long cartId, Long menuId);

    // 注文確定後、カートを空にするため
    void deleteByCartId(Long cartId);
}
