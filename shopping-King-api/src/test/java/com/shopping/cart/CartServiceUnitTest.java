package com.shopping.cart;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.shopping.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.shopping.dto.CartChangeDto;
import com.shopping.entity.Cart;
import com.shopping.repository.CartRepository;
import com.shopping.enums.DeleteAt;
import com.shopping.entity.Product;
import com.shopping.repository.ProductRepository;

@SpringBootTest
class CartServiceUnitTest {

  @Mock
  private CartRepository cartRepository;

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private CartService cartService;

  @Test
  @DisplayName("카트 조회")
  void findCartsByMemberId() {
    Long memberId = 1L;
    Cart cart1 = Cart.builder().id(1L).memberId(memberId).build();
    Cart cart2 = Cart.builder().id(2L).memberId(memberId).build();
    List<Cart> carts = Arrays.asList(cart1, cart2);

    when(cartRepository.findByMemberId(memberId)).thenReturn(carts);

    List<Cart> result = cartService.findCartsByMemberId(memberId);

    assertEquals(carts, result);
  }

  @Test
  @DisplayName("카트 저장")
  void saveCart() {
    // Mock data
    Long productId = 1L;
    Long memberId = 1L;
    CartChangeDto cartChangeDto = CartChangeDto.builder()
        .productId(productId)
        .memberId(memberId)
        .quantity(2)
        .price(100)
        .deleteAt(DeleteAt.N)
        .build();
    Product product = Product.builder().id(productId).build();
    Cart cart = Cart.builder()
        .id(1L)
        .memberId(memberId)
        .product(product)
        .quantity(2)
        .price(100)
        .deleteAt(DeleteAt.N)
        .build();

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(cartRepository.save(any(Cart.class))).thenReturn(cart);

    Cart savedCart = cartService.saveCart(cartChangeDto);

    assertNotNull(savedCart);
    assertEquals(cart.getId(), savedCart.getId());
    assertEquals(cart.getMemberId(), savedCart.getMemberId());
    assertEquals(cart.getProduct(), savedCart.getProduct());
    assertEquals(cart.getQuantity(), savedCart.getQuantity());
    assertEquals(cart.getPrice(), savedCart.getPrice());
    assertEquals(cart.getDeleteAt(), savedCart.getDeleteAt());
  }
}
