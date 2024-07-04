package com.shopping.mapper;

import java.util.Optional;
import com.shopping.entity.Category;
import com.shopping.dto.ProductRequestDto;
import com.shopping.dto.ProductResponseDto;
import com.shopping.entity.Product;

public class ProductMapper {

  public static ProductResponseDto productToProductDto(Product product) {
    return product == null ? null
        : ProductResponseDto
            .builder()
            .productId(product.getProductId())
            .productNm(product.getProductNm())
            .stockQuantity(product.getStockAmount())
            .salesRate(product.getSalesRate())
            .category(
                Optional.ofNullable(product.getCategory()).map(Category::getName).orElse(null))
            .price(product.getProductPrice())
            .discountRate(product.getDiscountRate())
            .discountPrice(product.getDiscountPrice())
            .registDt(product.getRegistDt())
            .deleteAt(product.getDeleteAt())
            .description(product.getDescription())
            .build();
  }

  public static Product productRequestDtoToProduct(ProductRequestDto dto, Category category) {
    return Product
        .builder()
        .productNm(dto.getProductNm())
        .productPrice(dto.getProductPrice())
        .stockAmount(dto.getStockAmount())
        .salesRate(dto.getSalesRate())
        .discountRate(dto.getDiscountRate())
        .discountPrice(dto.getDiscountPrice())
        .registDt(dto.getRegistDt())
        .description(dto.getDescription())
        .deleteAt(dto.getDeleteAt())
        .category(category)
        .build();
  }

}
