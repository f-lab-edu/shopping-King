package com.shopping.dto;

import com.shopping.enums.ProductStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

  // 상품명
  private String productName;
  // 상품id
  private Long productId;
  // 재고량
  private int stockQuantity;
  // 판매량
  private int salesRate;
  // 카테고리
  private String category;
  // 가격
  private int price;
  // 할인율
  private Integer discountRate;
  // 할인가
  private Integer discountPrice;
  // 상품등록일자
  private LocalDateTime registrationDate;
  // 상품 상태코드
  private ProductStatus statusCode;
  // 상품 설명
  private String description;

  public int getDiscountPrice() {
    return discountPrice != null ? discountPrice : price;
  }


}
