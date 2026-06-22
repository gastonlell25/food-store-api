package com.prog3.food_store_api.services;

import com.prog3.food_store_api.DTOs.OrderCreate;
import com.prog3.food_store_api.DTOs.OrderDetailCreate;
import com.prog3.food_store_api.DTOs.OrderDto;
import com.prog3.food_store_api.DTOs.OrderMapper;
import com.prog3.food_store_api.exceptions.ResourceNotFoundException;
import com.prog3.food_store_api.models.Order;
import com.prog3.food_store_api.models.OrderDetail;
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

    @Transactional
    public OrderDto create(OrderCreate dto) {
        Order order = Order.builder()
                .date(LocalDate.now())
                .status(dto.status())
                .paymentMethod(dto.paymentMethod())
                .user(userService.findEntityById(dto.userId()))
                .build();

        addDetails(order, dto.orderDetails());

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public OrderDto update(Long id, OrderCreate dto) {
        Order order = findEntityById(id);

        order.setStatus(dto.status());
        order.setPaymentMethod(dto.paymentMethod());
        order.setUser(userService.findEntityById(dto.userId()));

        order.getOrderDetails().clear();
        addDetails(order, dto.orderDetails());

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public void delete(Long id) {
        Order order = findEntityById(id);
        order.setDeleted(true);
        orderRepository.save(order);
    }

    private void addDetails(Order order, List<OrderDetailCreate> detailsDto) {
        detailsDto.forEach(d -> {
            OrderDetail detail = OrderDetail.builder()
                    .product(productService.findEntityById(d.productId()))
                    .quantity(d.quantity())
                    .build();
            order.addOrderDetail(detail);
        });
    }

    private Order findEntityById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }
}
