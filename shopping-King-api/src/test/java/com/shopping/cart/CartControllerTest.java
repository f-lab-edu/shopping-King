package com.shopping.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dto.CartChangeDto;
import com.shopping.entity.Cart;
import com.shopping.repository.CartRepository;
import com.shopping.service.CartService;
import com.shopping.web.CartController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc
class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CartRepository cartRepository;

  @MockBean
  private CartService cartService;

  @Test
  @DisplayName("장바구니 값이 있을때")
  void findCartListWithItems() throws Exception {
    Cart cart1 = Cart.builder()
        .id(1L)
        .memberId(1L)
        .quantity(2)
        .price(100)
        .build();
    List<Cart> cartList = Collections.singletonList(cart1);

    when(cartRepository.findByMemberId(1L)).thenReturn(cartList);

    mockMvc.perform(get("/cart/list/{memberId}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].memberId").value(1))
        .andExpect(jsonPath("$[0].quantity").value(2))
        .andExpect(jsonPath("$[0].price").value(100));

    verify(cartService, times(1)).findCartsByMemberId(1L);
  }

  @Test
  @DisplayName("장바구니 값이 없을때")
  void findCartListWithoutItems() throws Exception {
    // Mock data without items
    List<Cart> cartList = Collections.emptyList();

    when(cartRepository.findByMemberId(1L)).thenReturn(cartList);

    mockMvc.perform(get("/cart/list/{memberId}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());

    verify(cartService, times(1)).findCartsByMemberId(1L);
  }

  @Test
  @DisplayName("장바구니 저장 성공")
  void saveCartSuccess() throws Exception {
    CartChangeDto cartChangeDto = CartChangeDto.builder()
        .productId(1L)
        .memberId(1L)
        .quantity(2)
        .price(100)
        .build();
    Cart savedCart = Cart.builder()
        .id(1L)
        .memberId(1L)
        .quantity(2)
        .price(100)
        .build();

    when(cartService.saveCart(any(CartChangeDto.class))).thenReturn(savedCart);

    mockMvc.perform(put("/cart/change")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cartChangeDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.memberId").value(1))
        .andExpect(jsonPath("$.quantity").value(2))
        .andExpect(jsonPath("$.price").value(100));

    verify(cartService, times(1)).saveCart(any(CartChangeDto.class));
  }

  @Test
  @DisplayName("장바구니 저장 실패")
  void saveCartFailure() throws Exception {
    CartChangeDto cartChangeDto = CartChangeDto.builder()
        .productId(1L)
        .memberId(1L)
        .quantity(2)
        .price(100)
        .build();

    when(cartService.saveCart(any(CartChangeDto.class))).thenReturn(null);

    mockMvc.perform(put("/cart/change")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cartChangeDto)))
        .andExpect(status().isInternalServerError());

    verify(cartService, times(1)).saveCart(any(CartChangeDto.class));
  }
}
