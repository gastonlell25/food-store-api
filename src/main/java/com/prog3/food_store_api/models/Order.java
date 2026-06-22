package com.prog3.food_store_api.models;

import com.prog3.food_store_api.models.enums.OrderStatus;
import com.prog3.food_store_api.models.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@SQLRestriction("deleted = false")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class Order extends Base {

    @Column(updatable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    public void addOrderDetail(OrderDetail detail) {
        this.orderDetails.add(detail);
        detail.setOrder(this);
        detail.calculateSubtotal();
        recalculateTotal();
    }

    public void recalculateTotal() {
        this.total = this.orderDetails.stream()
                .map(OrderDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
