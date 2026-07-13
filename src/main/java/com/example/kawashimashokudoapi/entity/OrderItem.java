package com.example.kawashimashokudoapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(
    name = "order_items",
    indexes = {
        @Index(name = "idx_order_items_order_id", columnList = "order_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // 参照用。表示にはスナップショット列(menuNameSnapshot, priceSnapshot)を優先する
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    // 注文時点のメニュー名を保存(後からの名称変更に影響されない)
    @Column(name = "menu_name_snapshot", nullable = false, length = 100)
    private String menuNameSnapshot;

    // 注文時点の価格を保存(後からの値上げ/値下げに影響されない)
    @Column(name = "price_snapshot", nullable = false)
    private int priceSnapshot;

    @Column(nullable = false)
    private int quantity;
}
