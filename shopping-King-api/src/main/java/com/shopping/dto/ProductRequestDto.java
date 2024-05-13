package com.shopping.dto;

import com.shopping.enums.DeleteAt;
import io.micrometer.common.lang.Nullable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

  @Nullable
  private Long id;

  private String productNm;

  private int productPrice;

  private int stockAmount;

  private int salesRate;

  private int discountRate;

  private int discountPrice;

  private LocalDateTime registDt;

  private String description;

  private DeleteAt deleteAt;

  private Long categoryId;


}
