package com.example.kawashimashokudoapi.repository;

import com.example.kawashimashokudoapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // KOMOJU Webhook受信時、komojuPaymentIdから対象のPaymentを特定するため
    // (これがないとWebhookでどの注文の決済か分からない)
    Optional<Payment> findByKomojuPaymentId(String komojuPaymentId);

    // 注文詳細画面などで、注文IDから決済情報を取得するため
    Optional<Payment> findByOrderId(Long orderId);
}
