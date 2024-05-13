package com.shopping.dto;

import com.shopping.enums.OrderStatus;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDto {

  // 유저id
  private long memberId;
  // 상품
  private ProductDto productDto;
  // 주문시간
  private LocalDateTime orderedTime;
  // 수량
  private int quantity;
  // 가격
  private int price;
  // 주문상태
  private OrderStatus orderStatus;

  public int getPrice() {
    if (productDto != null) {
      return productDto.getPrice() * quantity;

    } else {
      return 0;
    }
  }
}
