package com.prog3.food_store_api.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class Product extends Base {

    @Column(nullable = false)
    private String name;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    private String description;
    private int stock;

    @Column(length = 500)
    private String image;

    @Builder.Default
    private boolean available = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
