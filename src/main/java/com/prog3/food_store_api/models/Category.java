package com.prog3.food_store_api.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "categories")
@SQLRestriction("deleted = false")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class Category extends Base {

    @Column(nullable = false, length = 100)
    private String name;

    private String description;
}
