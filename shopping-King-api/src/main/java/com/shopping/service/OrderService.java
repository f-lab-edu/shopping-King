package com.shopping.service;

import com.shopping.mapper.OrderMapper;
import com.shopping.repository.MemberRepository;
import com.shopping.dto.OrderRequestDto;
import com.shopping.entity.Orders;
import com.shopping.repository.OrderRepository;
import com.shopping.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;

  public List<Orders> findOrdersByMemberId(Long memberId) {
    return Optional
        .ofNullable(orderRepository.findByMemberId(memberId))
        .orElseThrow(() -> new RuntimeException("Order not found with memberId: " + memberId));
  }

  public Orders findOrdersByOrderId(Long orderId) {
    return orderRepository
        .findById(orderId)
        .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
  }

  public Orders changeOrder(OrderRequestDto orderRequestDto) {
    return orderRepository
        .save(OrderMapper
            .orderRequestDtoToOrders(orderRequestDto,
                memberRepository
                    .findById(orderRequestDto.getMemberId())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Member not found with id: " + orderRequestDto.getMemberId())),
                productRepository
                    .findById(orderRequestDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Product not found with id: " + orderRequestDto.getProductId()))));
  }
}
