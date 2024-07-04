package com.shopping.mapper;

import com.shopping.entity.Member;
import com.shopping.dto.OrderRequestDto;
import com.shopping.dto.OrderResponseDto;
import com.shopping.entity.Orders;
import com.shopping.entity.Product;

public class OrderMapper {

  public static OrderResponseDto ordertoOrderResponseDto(Orders order) {
    return OrderResponseDto
        .builder()
        .orderId(order.getOrderId())
        .orderDt(order.getOrderDt())
        .address(order.getAddress())
        .quantity(order.getQuantity())
        .price(order.getPrice())
        .orderStatus(order.getOrderStatus())
        .product(order.getProduct())
        .build();
  }

  public static Orders orderRequestDtoToOrders(OrderRequestDto orderRequestDto, Member member,
      Product product) {
    return Orders
        .builder()
        .price(orderRequestDto.getPrice())
        .orderDt(orderRequestDto.getOrderDt())
        .quantity(orderRequestDto.getQuantity())
        .address(orderRequestDto.getAddress())
        .orderStatus(orderRequestDto.getOrderStatus())
        .member(member)
        .product(product)
        .build();
  }
}
