package com.shopping.web;

import com.shopping.mapper.ProductMapper;
import com.shopping.dto.ProductRequestDto;
import com.shopping.dto.ProductResponseDto;
import com.shopping.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping(value = "/list/{categoryId}")
  public ResponseEntity<List<ProductResponseDto>> findProductList(@PathVariable Long categoryId) {
    return ResponseEntity
        .ok(productService
            .findCategoryList(categoryId)
            .stream()
            .map(ProductMapper::productToProductDto)
            .collect(Collectors.toList()));
  }

  @GetMapping("/detail/{productId}")
  public ResponseEntity<ProductResponseDto> findProductDetail(@PathVariable Long productId) {
    return ResponseEntity
        .ok(ProductMapper.productToProductDto(productService.findProudctByProductId(productId)));
  }

  @PostMapping("/add")
  public ResponseEntity<ProductResponseDto> addProduct(
      @RequestBody ProductRequestDto productRequestDto) {
    return ResponseEntity
        .ok(ProductMapper.productToProductDto(productService.changeProduct(productRequestDto)));
  }

  @PutMapping("/change")
  public ResponseEntity<ProductResponseDto> changeProduct(
      @RequestBody ProductRequestDto productRequestDto) {
    return ResponseEntity
        .ok(ProductMapper.productToProductDto(productService.changeProduct(productRequestDto)));
  }

  @DeleteMapping("/delete/{productId}")
  public ResponseEntity<Long> deleteProduct(@PathVariable Long productId) {
    return ResponseEntity.ok(productId);
  }

}
