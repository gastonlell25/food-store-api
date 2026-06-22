package com.prog3.food_store_api.DTOs;

import com.prog3.food_store_api.models.Order;
import com.prog3.food_store_api.models.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class})
public interface OrderMapper {

    OrderDto toDto(Order order);

    OrderDetailDto toDto(OrderDetail orderDetail);
}
