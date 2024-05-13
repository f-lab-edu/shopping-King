package com.shopping.service;

import com.shopping.repository.CategoryRepository;
import com.shopping.mapper.ProductMapper;
import com.shopping.dto.ProductRequestDto;
import com.shopping.entity.Product;
import com.shopping.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public List<Product> findCategoryList(Long categoryId) {
    return
        (categoryId == null)
            ? productRepository.findAll()
            : productRepository.findAllByCategoryId(categoryId);
  }

  public Product findProudctByProductId(Long productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
  }

  public Product changeProduct(ProductRequestDto productRequestDto) {
    return productRepository
        .save(ProductMapper
            .productRequestDtoToProduct(productRequestDto,
                categoryRepository
                    .findById(productRequestDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException(
                        "Category not found with id: " + productRequestDto.getCategoryId()))));
  }
}
