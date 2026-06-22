package com.prog3.food_store_api.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@ToString
public class OrderDetail extends Base {

    private int quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    public void calculateSubtotal() {
        if (this.product != null && this.quantity > 0) {
            this.subtotal = this.product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }
}
