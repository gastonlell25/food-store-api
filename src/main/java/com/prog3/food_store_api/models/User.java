package com.prog3.food_store_api.models;

import com.prog3.food_store_api.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@SQLRestriction("deleted = false")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class User extends Base {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Order> orders = new LinkedHashSet<>();

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }
}
