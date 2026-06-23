package com.prog3.food_store_api.services;

import com.prog3.food_store_api.DTOs.OrderCreate;
import com.prog3.food_store_api.DTOs.OrderDto;
import com.prog3.food_store_api.DTOs.OrderMapper;
import com.prog3.food_store_api.DTOs.OrderUpdate;
import com.prog3.food_store_api.exceptions.BusinessException;
import com.prog3.food_store_api.models.Order;
import com.prog3.food_store_api.models.OrderDetail;
import com.prog3.food_store_api.models.Product;
import com.prog3.food_store_api.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderDto getById(Long id) {
        return orderMapper.toDto(orderRepository.findByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getByUser(Long userId) {
        userService.findEntityById(userId);
        return orderRepository.findAllByUserIdAndDeletedFalse(userId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional
    public OrderDto create(OrderCreate dto) {
        Order order = Order.builder()
                .date(LocalDate.now())
                .status(dto.status())
                .paymentMethod(dto.paymentMethod())
                .user(userService.findEntityById(dto.userId()))
                .build();

        dto.orderDetails().forEach(d -> {
            Product product = productService.findEntityById(d.productId());

            if (!product.isAvailable()) {
                throw new BusinessException(
                        "El producto '" + product.getName() + "' no está disponible para la venta");
            }
            if (product.getStock() < d.quantity()) {
                throw new BusinessException(
                        "Stock insuficiente para '" + product.getName() +
                        "'. Disponible: " + product.getStock() + ", Solicitado: " + d.quantity());
            }

            product.setStock(product.getStock() - d.quantity());

            order.addOrderDetail(OrderDetail.builder()
                    .product(product)
                    .quantity(d.quantity())
                    .build());
        });

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public OrderDto update(Long id, OrderUpdate dto) {
        Order order = orderRepository.findByIdOrThrow(id);

        if (dto.status() != null) order.setStatus(dto.status());
        if (dto.paymentMethod() != null) order.setPaymentMethod(dto.paymentMethod());

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findByIdOrThrow(id);
        order.setDeleted(true);
        orderRepository.save(order);
    }
}
