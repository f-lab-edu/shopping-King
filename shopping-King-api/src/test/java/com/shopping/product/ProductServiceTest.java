package com.shopping.product;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.shopping.entity.Category;
import com.shopping.repository.CategoryRepository;
import com.shopping.enums.DeleteAt;
import com.shopping.dto.ProductRequestDto;
import com.shopping.entity.Product;
import com.shopping.repository.ProductRepository;
import com.shopping.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("ProductService 테스트")
public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private ProductService productService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("카테고리별 상품 목록 조회")
  public void testFindProductListByCategory() {
    Long categoryId = 1L;
    List<Product> productList = new ArrayList<>();
    productList.add(createProduct(categoryId));
    when(productRepository.findAllByCategoryId(categoryId)).thenReturn(productList);

    List<Product> foundProducts = productService.findCategoryList(categoryId);

    assertNotNull(foundProducts);
    assertEquals(1, foundProducts.size());
  }

  @Test
  @DisplayName("카테고리가 null인 경우 모든 상품 목록 조회")
  public void testFindAllProducts() {
    List<Product> productList = new ArrayList<>();
    productList.add(createProduct(1L));
    when(productRepository.findAll()).thenReturn(productList);

    List<Product> foundProducts = productService.findCategoryList(null);

    assertNotNull(foundProducts);
    assertEquals(1, foundProducts.size());
  }

  @Test
  @DisplayName("상품 ID로 상품 조회 - 존재하는 경우")
  public void testFindProductByProductIdExists() {
    Long productId = 1L;
    Product product = createProduct(productId);
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    Product foundProduct = productService.findProudctByProductId(productId);

    assertNotNull(foundProduct);
    assertEquals(productId, foundProduct.getId());
  }

  @Test
  @DisplayName("상품 ID로 상품 조회 - 존재하지 않는 경우")
  public void testFindProductByProductIdNotExists() {
    Long productId = 1L;
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> {
      productService.findProudctByProductId(productId);
    });
  }

  @Test
  @DisplayName("상품 변경")
  public void testChangeProduct() {
    Long categoryId = 1L;
    ProductRequestDto productRequestDto = createProductRequestDto(categoryId);
    Category category = Category.builder().Id(categoryId).name("Category1").deleteAt(DeleteAt.N)
        .build();
    Product product = createProduct(categoryId);
    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
    when(productRepository.save(any())).thenReturn(product);

    Product savedProduct = productService.changeProduct(productRequestDto);

    assertNotNull(savedProduct);
    assertEquals(productRequestDto.getProductNm(), savedProduct.getProductNm());
    assertEquals(productRequestDto.getProductPrice(), savedProduct.getProductPrice());
    assertEquals(productRequestDto.getStockAmount(), savedProduct.getStockAmount());
    assertEquals(productRequestDto.getSalesRate(), savedProduct.getSalesRate());
    assertEquals(productRequestDto.getDiscountRate(), savedProduct.getDiscountRate());
    assertEquals(productRequestDto.getDiscountPrice(), savedProduct.getDiscountPrice());
    assertEquals(productRequestDto.getRegistDt(), savedProduct.getRegistDt());
    assertEquals(productRequestDto.getDescription(), savedProduct.getDescription());
    assertEquals(categoryId, savedProduct.getCategory().getId());
  }

  private Product createProduct(Long categoryId) {
    return Product.builder()
        .id(1L)
        .productNm("Product1")
        .productPrice(10000)
        .stockAmount(10)
        .salesRate(0)
        .discountRate(0)
        .discountPrice(10000)
        .registDt(LocalDateTime.now())
        .description("Description")
        .deleteAt(DeleteAt.N)
        .category(Category.builder().Id(categoryId).name("Category1").deleteAt(DeleteAt.N).build())
        .build();
  }

  private ProductRequestDto createProductRequestDto(Long categoryId) {
    return ProductRequestDto.builder()
        .productNm("Product1")
        .productPrice(10000)
        .stockAmount(10)
        .salesRate(0)
        .discountRate(0)
        .discountPrice(10000)
        .registDt(LocalDateTime.now())
        .description("Description")
        .deleteAt(DeleteAt.N)
        .categoryId(categoryId)
        .build();
  }
}
