package com.shopping.mapper;

import com.shopping.dto.CartChangeDto;
import com.shopping.dto.CartResponseDto;
import com.shopping.entity.Cart;
import com.shopping.entity.Product;

public class CartMapper {

  public static CartResponseDto cartToCartResponseDto(Cart cart) {
    return cart == null || cart.getProduct() == null ? null
        : CartResponseDto
            .builder()
            .cartId(cart.getId())
            .memberId(cart.getId())
            .quantity(cart.getQuantity())
            .price(cart.getProduct().getProductPrice())
            .productId(cart.getProduct().getProductId())
            .productNm(cart.getProduct().getProductNm())
            .productDiscountPrice(cart.getProduct().getDiscountPrice())
            .productDiscountRate(cart.getProduct().getDiscountRate())
            .productPrice(cart.getProduct().getProductPrice())
            .build();
  }

  public static Cart cartChangeDtoToCart(CartChangeDto cartChangeDto, Product product) {
    return cartChangeDto == null || product == null ? null
        : Cart
            .builder()
            .memberId(cartChangeDto.getMemberId())
            .product(product)
            .quantity(cartChangeDto.getQuantity())
            .price(cartChangeDto.getPrice())
            .build();
  }
}
