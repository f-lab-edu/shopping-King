package com.shopping.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopping.dto.OrderRequestDto;
import com.shopping.entity.Orders;
import com.shopping.entity.Product;
import com.shopping.enums.OrderStatus;
import com.shopping.service.OrderService;
import com.shopping.web.OrderController;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  private ObjectMapper objectMapper;

  @InjectMocks
  private OrderController orderController;

  @BeforeEach
  void setup() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Test
  @WithMockUser
  void testFindOrderList() throws Exception {
    Orders order1 = Orders.builder()
        .orderId(1L)
        .orderDt(LocalDateTime.now())
        .address("123 Street")
        .quantity(2)
        .price(200)
        .orderStatus(OrderStatus.PAYMENT)
        .product(new Product())
        .build();

    Orders order2 = Orders.builder()
        .orderId(2L)
        .orderDt(LocalDateTime.now())
        .address("456 Avenue")
        .quantity(3)
        .price(300)
        .orderStatus(OrderStatus.SHIPPING)
        .product(new Product())
        .build();

    List<Orders> ordersList = Arrays.asList(order1, order2);

    Mockito.when(orderService.findOrdersByMemberId(1L)).thenReturn(ordersList);

    mockMvc.perform(get("/order/list/{memberId}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].orderId").value(order1.getOrderId()))
        .andExpect(jsonPath("$[0].address").value(order1.getAddress()))
        .andExpect(jsonPath("$[0].quantity").value(order1.getQuantity()))
        .andExpect(jsonPath("$[0].price").value(order1.getPrice()))
        .andExpect(jsonPath("$[0].orderStatus").value(order1.getOrderStatus().toString()))
        .andExpect(jsonPath("$[1].orderId").value(order2.getOrderId()))
        .andExpect(jsonPath("$[1].address").value(order2.getAddress()))
        .andExpect(jsonPath("$[1].quantity").value(order2.getQuantity()))
        .andExpect(jsonPath("$[1].price").value(order2.getPrice()))
        .andExpect(jsonPath("$[1].orderStatus").value(order2.getOrderStatus().toString()));
  }

  @Test
  @WithMockUser
  void testFindOrderDetail() throws Exception {
    Orders order = Orders.builder()
        .orderId(1L)
        .orderDt(LocalDateTime.now())
        .address("123 Street")
        .quantity(2)
        .price(200)
        .orderStatus(OrderStatus.PAYMENT)
        .product(new Product())
        .build();

    Mockito.when(orderService.findOrdersByOrderId(anyLong())).thenReturn(order);

    mockMvc.perform(get("/order/detail/{orderId}", 1L)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.orderId").value(order.getOrderId()))
        .andExpect(jsonPath("$.address").value(order.getAddress()))
        .andExpect(jsonPath("$.quantity").value(order.getQuantity()))
        .andExpect(jsonPath("$.price").value(order.getPrice()))
        .andExpect(jsonPath("$.orderStatus").value(order.getOrderStatus().toString()));
  }

  @Test
  @WithMockUser
  void testAddOrder() throws Exception {
    OrderRequestDto orderRequestDto1 = new OrderRequestDto(
        null, LocalDateTime.now(), "123 Street", 2, 200, OrderStatus.PAYMENT, 1L, 1L);
    OrderRequestDto orderRequestDto2 = new OrderRequestDto(
        null, LocalDateTime.now(), "456 Avenue", 3, 300, OrderStatus.SHIPPING, 2L, 2L);
    List<OrderRequestDto> orderRequestDtoList = Arrays.asList(orderRequestDto1, orderRequestDto2);

    Orders order1 = Orders.builder()
        .orderId(1L)
        .orderDt(LocalDateTime.now())
        .address("123 Street")
        .quantity(2)
        .price(200)
        .orderStatus(OrderStatus.PAYMENT)
        .product(new Product())
        .build();

    Orders order2 = Orders.builder()
        .orderId(2L)
        .orderDt(LocalDateTime.now())
        .address("456 Avenue")
        .quantity(3)
        .price(300)
        .orderStatus(OrderStatus.SHIPPING)
        .product(new Product())
        .build();

    Mockito.when(orderService.changeOrder(any(OrderRequestDto.class)))
        .thenReturn(order1)
        .thenReturn(order2);

    mockMvc.perform(post("/order/add")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(orderRequestDtoList)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].orderId").value(order1.getOrderId()))
        .andExpect(jsonPath("$[0].address").value(order1.getAddress()))
        .andExpect(jsonPath("$[0].quantity").value(order1.getQuantity()))
        .andExpect(jsonPath("$[0].price").value(order1.getPrice()))
        .andExpect(jsonPath("$[0].orderStatus").value(order1.getOrderStatus().toString()))
        .andExpect(jsonPath("$[1].orderId").value(order2.getOrderId()))
        .andExpect(jsonPath("$[1].address").value(order2.getAddress()))
        .andExpect(jsonPath("$[1].quantity").value(order2.getQuantity()))
        .andExpect(jsonPath("$[1].price").value(order2.getPrice()))
        .andExpect(jsonPath("$[1].orderStatus").value(order2.getOrderStatus().toString()));
  }
}
