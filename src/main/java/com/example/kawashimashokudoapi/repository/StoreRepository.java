package com.example.kawashimashokudoapi.repository;

import com.example.kawashimashokudoapi.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 「自店舗のみ」操作可能かの判定・自分の店舗情報取得のため
    Optional<Store> findByOwnerUserId(Long ownerUserId);

    // customer向け店舗一覧では、admin承認済みの店舗だけを表示するため
    List<Store> findByIsApprovedTrue();
}
