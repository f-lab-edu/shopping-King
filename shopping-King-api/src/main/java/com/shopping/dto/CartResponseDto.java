package com.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {

  private Long cartId;
  private Long memberId;
  private int quantity;
  private int price;
  private Long productId;
  private String productNm;
  private int productDiscountPrice;
  private int productDiscountRate;
  private int productPrice;


}
