package com.example.kawashimashokudoapi.repository;

import com.example.kawashimashokudoapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 権限表No.4「自分の注文履歴を見る」(customer向け)
    List<Order> findByCustomerId(Long customerId);

    // 権限表No.6「自店舗の注文一覧を見る」(store向け)
    List<Order> findByStoreId(Long storeId);

    // 権限表No.10「配達ステータスを更新する(自分の担当のみ)」(courier向け)
    List<Order> findByCourierId(Long courierId);

    // 権限表No.8「配達可能な注文一覧を見る」
    // = ステータスがready、かつまだ誰にもassignされてない(courierId = null)注文
    List<Order> findByStatusAndCourierIsNull(String status);
}
