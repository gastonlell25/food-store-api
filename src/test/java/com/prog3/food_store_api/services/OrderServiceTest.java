package com.prog3.food_store_api.services;

import com.prog3.food_store_api.DTOs.OrderCreate;
import com.prog3.food_store_api.DTOs.OrderDetailCreate;
import com.prog3.food_store_api.DTOs.OrderDto;
import com.prog3.food_store_api.DTOs.OrderMapper;
import com.prog3.food_store_api.DTOs.OrderUpdate;
import com.prog3.food_store_api.exceptions.BusinessException;
import com.prog3.food_store_api.exceptions.ResourceNotFoundException;
import com.prog3.food_store_api.models.Order;
import com.prog3.food_store_api.models.Product;
import com.prog3.food_store_api.models.User;
import com.prog3.food_store_api.models.enums.OrderStatus;
import com.prog3.food_store_api.models.enums.PaymentMethod;
import com.prog3.food_store_api.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private UserService userService;
    @Mock private ProductService productService;
    @Mock private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private Product buildProduct(boolean available, int stock) {
        Product p = Product.builder()
                .name("Pizza")
                .price(new BigDecimal("1200.00"))
                .available(available)
                .stock(stock)
                .build();
        return p;
    }

    @Test
    void create_throwsWhenProductUnavailable() {
        Product product = buildProduct(false, 10);
        OrderCreate dto = new OrderCreate(1L, PaymentMethod.CASH, OrderStatus.PENDING,
                List.of(new OrderDetailCreate(1L, 2)));

        when(userService.findEntityById(1L)).thenReturn(new User());
        when(productService.findEntityById(1L)).thenReturn(product);

        assertThatThrownBy(() -> orderService.create(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("no está disponible");
    }

    @Test
    void create_throwsWhenInsufficientStock() {
        Product product = buildProduct(true, 1);
        OrderCreate dto = new OrderCreate(1L, PaymentMethod.CASH, OrderStatus.PENDING,
                List.of(new OrderDetailCreate(1L, 5)));

        when(userService.findEntityById(1L)).thenReturn(new User());
        when(productService.findEntityById(1L)).thenReturn(product);

        assertThatThrownBy(() -> orderService.create(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Stock insuficiente");
    }

    @Test
    void create_reducesStockOnSuccess() {
        Product product = buildProduct(true, 10);
        OrderCreate dto = new OrderCreate(1L, PaymentMethod.CASH, OrderStatus.PENDING,
                List.of(new OrderDetailCreate(1L, 3)));
        Order savedOrder = Order.builder().build();
        OrderDto orderDto = new OrderDto(1L, null, OrderStatus.PENDING, PaymentMethod.CASH,
                BigDecimal.ZERO, null, List.of());

        when(userService.findEntityById(1L)).thenReturn(new User());
        when(productService.findEntityById(1L)).thenReturn(product);
        when(orderRepository.save(any())).thenReturn(savedOrder);
        when(orderMapper.toDto(savedOrder)).thenReturn(orderDto);

        orderService.create(dto);

        assertThat(product.getStock()).isEqualTo(7);
    }

    @Test
    void update_onlyChangesStatusAndPaymentMethod() {
        Order order = Order.builder()
                .status(OrderStatus.PENDING)
                .paymentMethod(PaymentMethod.CASH)
                .build();
        OrderUpdate update = new OrderUpdate(OrderStatus.DELIVERED, PaymentMethod.DEBIT_CARD);
        OrderDto orderDto = new OrderDto(1L, null, OrderStatus.DELIVERED, PaymentMethod.DEBIT_CARD,
                BigDecimal.ZERO, null, List.of());

        when(orderRepository.findByIdOrThrow(1L)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.update(1L, update);

        assertThat(result.status()).isEqualTo(OrderStatus.DELIVERED);
        assertThat(result.paymentMethod()).isEqualTo(PaymentMethod.DEBIT_CARD);
    }

    @Test
    void getById_throwsWhenNotFound() {
        when(orderRepository.findByIdOrThrow(99L))
                .thenThrow(new ResourceNotFoundException("Entidad con id 99 no encontrado"));

        assertThatThrownBy(() -> orderService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getAll_returnsMappedList() {
        Order order = Order.builder().build();
        OrderDto dto = new OrderDto(1L, null, OrderStatus.PENDING, PaymentMethod.CASH,
                BigDecimal.ZERO, null, List.of());

        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderMapper.toDto(order)).thenReturn(dto);

        List<OrderDto> result = orderService.getAll();

        assertThat(result).hasSize(1);
    }
}
