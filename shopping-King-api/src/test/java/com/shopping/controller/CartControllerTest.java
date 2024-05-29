package com.shopping.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dto.CartChangeDto;
import com.shopping.dto.CartResponseDto;
import com.shopping.entity.Cart;
import com.shopping.entity.Product;
import com.shopping.enums.DeleteAt;
import com.shopping.mapper.CartMapper;
import com.shopping.service.CartService;
import com.shopping.web.CartController;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartController.class)
class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CartService cartService;

  @InjectMocks
  private CartController cartController;

  @Test
  @DisplayName("장바구니 값이 있을때")
  @WithMockUser
  void findCartListWithItems() throws Exception {
    Product product = Product.builder()
        .productId(1L)
        .productNm("Product1")
        .productPrice(100)
        .discountPrice(90)
        .discountRate(10)
        .build();
    Cart cart = Cart.builder()
        .id(1L)
        .memberId(1L)
        .quantity(2)
        .price(100)
        .product(product)
        .deleteAt(DeleteAt.N)
        .build();
    List<Cart> cartList = Collections.singletonList(cart);

    CartResponseDto cartResponseDto = CartMapper.cartToCartResponseDto(cart);
    List<CartResponseDto> cartResponseList = Collections.singletonList(cartResponseDto);

    when(cartService.findCartsByMemberId(1L)).thenReturn(cartList);

    mockMvc.perform(get("/cart/list/{memberId}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].cartId").value(1))
        .andExpect(jsonPath("$[0].memberId").value(1))
        .andExpect(jsonPath("$[0].quantity").value(2))
        .andExpect(jsonPath("$[0].price").value(100))
        .andExpect(jsonPath("$[0].productId").value(1))
        .andExpect(jsonPath("$[0].productNm").value("Product1"))
        .andExpect(jsonPath("$[0].productDiscountPrice").value(90))
        .andExpect(jsonPath("$[0].productDiscountRate").value(10))
        .andExpect(jsonPath("$[0].productPrice").value(100));

    verify(cartService, times(1)).findCartsByMemberId(1L);
  }

  @Test
  @DisplayName("장바구니 값이 없을때")
  @WithMockUser
  void findCartListWithoutItems() throws Exception {
    List<Cart> cartList = Collections.emptyList();

    when(cartService.findCartsByMemberId(1L)).thenReturn(cartList);

    mockMvc.perform(get("/cart/list/{memberId}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());

    verify(cartService, times(1)).findCartsByMemberId(1L);
  }

  @Test
  @DisplayName("장바구니 저장 성공")
  @WithMockUser
  void saveCartSuccess() throws Exception {
    CartChangeDto cartChangeDto = CartChangeDto.builder()
        .productId(1L)
        .memberId(1L)
        .quantity(2)
        .price(100)
        .build();

    Product product = Product.builder()
        .productId(1L)
        .productNm("Product1")
        .productPrice(100)
        .discountPrice(90)
        .discountRate(10)
        .build();

    Cart savedCart = Cart.builder()
        .id(1L)
        .memberId(1L)
        .quantity(2)
        .price(100)
        .product(product)
        .deleteAt(DeleteAt.N)
        .build();

    CartResponseDto savedCartResponseDto = CartMapper.cartToCartResponseDto(savedCart);

    when(cartService.saveCart(any(CartChangeDto.class))).thenReturn(savedCart);

    mockMvc.perform(put("/cart/change")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cartChangeDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.cartId").value(1))
        .andExpect(jsonPath("$.memberId").value(1))
        .andExpect(jsonPath("$.quantity").value(2))
        .andExpect(jsonPath("$.price").value(100))
        .andExpect(jsonPath("$.productId").value(1))
        .andExpect(jsonPath("$.productNm").value("Product1"))
        .andExpect(jsonPath("$.productDiscountPrice").value(90))
        .andExpect(jsonPath("$.productDiscountRate").value(10))
        .andExpect(jsonPath("$.productPrice").value(100));

    verify(cartService, times(1)).saveCart(any(CartChangeDto.class));
  }

  @Test
  @DisplayName("장바구니 저장 실패")
  @WithMockUser
  void saveCartFailure() throws Exception {
    CartChangeDto cartChangeDto = CartChangeDto.builder()
        .productId(1L)
        .memberId(1L)
        .quantity(2)
        .price(100)
        .build();

    when(cartService.saveCart(any(CartChangeDto.class))).thenReturn(null);

    when(cartService.saveCart(any(CartChangeDto.class))).thenReturn(null);

    mockMvc.perform(put("/cart/change")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cartChangeDto)))
        .andExpect(status().isOk());

    verify(cartService, times(1)).saveCart(any(CartChangeDto.class));
  }
}
