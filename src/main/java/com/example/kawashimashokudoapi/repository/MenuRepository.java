package com.example.kawashimashokudoapi.repository;

import com.example.kawashimashokudoapi.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    // 店舗管理画面向け:自店舗の全メニュー(売り切れ含む)を取得
    List<Menu> findByStoreId(Long storeId);

    // customer向け:購入可能なメニューだけを取得(is_available = true)
    List<Menu> findByStoreIdAndIsAvailableTrue(Long storeId);
}
