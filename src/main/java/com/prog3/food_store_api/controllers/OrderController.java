package com.prog3.food_store_api.controllers;

import com.prog3.food_store_api.DTOs.OrderCreate;
import com.prog3.food_store_api.DTOs.OrderDto;
import com.prog3.food_store_api.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(@Valid @RequestBody OrderCreate dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> update(@PathVariable Long id, @Valid @RequestBody OrderCreate dto) {
        return ResponseEntity.ok(orderService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
