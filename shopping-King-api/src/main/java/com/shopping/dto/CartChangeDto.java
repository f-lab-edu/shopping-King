package com.shopping.dto;

import com.shopping.enums.DeleteAt;
import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartChangeDto {

  @Nullable
  private Long cartId;
  private Long memberId;
  private Long productId;
  private int quantity;
  private int price;
  private DeleteAt deleteAt;
}
