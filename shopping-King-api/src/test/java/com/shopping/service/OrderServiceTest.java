package com.shopping.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.shopping.dto.OrderRequestDto;
import com.shopping.entity.Member;
import com.shopping.entity.Orders;
import com.shopping.entity.Product;
import com.shopping.enums.OrderStatus;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderRepository;
import com.shopping.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("OrderService 테스트")
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private OrderService orderService;

  @Test
  @DisplayName("특정 회원의 주문 목록 조회 - 회원이 존재하는 경우")
  public void testFindOrdersByMemberId_WhenMemberExists() {
    Long memberId = 1L;
    Member member = Member.builder().id(memberId).name("User1").build();
    List<Orders> orders = new ArrayList<>();
    orders.add(Orders.builder().orderId(1L).member(member).orderDt(LocalDateTime.now()).build());
    when(orderRepository.findByMemberId(memberId)).thenReturn(orders);

    List<Orders> foundOrders = orderService.findOrdersByMemberId(memberId);

    assertEquals(orders, foundOrders);
  }

  @Test
  @DisplayName("특정 회원의 주문 목록 조회 - 회원이 존재하지 않는 경우")
  public void testFindOrdersByMemberId_WhenMemberNotExists() {
    Long memberId = 1L;
    when(orderRepository.findByMemberId(memberId)).thenReturn(null);

    assertThrows(RuntimeException.class, () -> {
      orderService.findOrdersByMemberId(memberId);
    });
  }

  @Test
  @DisplayName("특정 주문 조회 - 주문이 존재하는 경우")
  public void testFindOrdersByOrderId_WhenOrderExists() {
    Long orderId = 1L;
    Orders order = Orders.builder().orderId(orderId).orderDt(LocalDateTime.now()).build();
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    Orders foundOrder = orderService.findOrdersByOrderId(orderId);

    assertEquals(order, foundOrder);
  }

  @Test
  @DisplayName("특정 주문 조회 - 주문이 존재하지 않는 경우")
  public void testFindOrdersByOrderId_WhenOrderNotExists() {
    Long orderId = 1L;
    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> {
      orderService.findOrdersByOrderId(orderId);
    });
  }

  @Test
  @DisplayName("주문 생성")
  public void testChangeOrder() {
    // Mock 데이터 생성
    Long memberId = 1L;
    Long productId = 1L;
    OrderRequestDto orderRequestDto = OrderRequestDto.builder()
        .memberId(memberId)
        .productId(productId)
        .quantity(1)
        .price(10000)
        .orderStatus(OrderStatus.PAYMENT)
        .build();

    Member member = Member.builder().id(memberId).name("User1").build();
    Product product = Product.builder().productId(productId).productNm("Product1").build();

    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(orderRepository.save(any())).thenAnswer(invocation -> {
      Orders order = invocation.getArgument(0);
      return Orders.builder()
          .orderId(1L) // 임의의 값 설정
          .member(order.getMember())
          .product(order.getProduct())
          .quantity(order.getQuantity())
          .price(order.getPrice())
          .orderDt(order.getOrderDt())
          .address(order.getAddress())
          .orderStatus(order.getOrderStatus())
          .build();
    });

    Orders result = orderService.changeOrder(orderRequestDto);

    assertNotNull(result);
    assertEquals(memberId, result.getMember().getId());
    assertEquals(productId, result.getProduct().getProductId());
    assertEquals(orderRequestDto.getQuantity(), result.getQuantity());
    assertEquals(orderRequestDto.getPrice(), result.getPrice());
    assertEquals(orderRequestDto.getOrderStatus(), result.getOrderStatus());
  }


}
