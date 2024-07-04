package com.shopping.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopping.dto.ProductRequestDto;
import com.shopping.entity.Category;
import com.shopping.enums.DeleteAt;
import com.shopping.mapper.ProductMapper;
import com.shopping.service.ProductService;
import com.shopping.web.ProductController;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Test
  @WithMockUser
  public void testFindProductList() throws Exception {
    Mockito.when(productService.findCategoryList(1L))
        .thenReturn(Collections.singletonList(ProductMapper.productRequestDtoToProduct(
            ProductRequestDto.builder()
                .productNm("Test Product")
                .productPrice(1000)
                .stockAmount(100)
                .salesRate(10)
                .discountRate(10)
                .discountPrice(900)
                .registDt(LocalDateTime.now())
                .description("Test Description")
                .deleteAt(DeleteAt.N)
                .categoryId(1L)
                .build(),
            null)));

    mockMvc.perform(get("/product/list/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].productNm", is("Test Product")))
        .andExpect(jsonPath("$[0].price", is(1000)));
  }

  @Test
  @WithMockUser
  public void testFindProductDetail() throws Exception {
    Mockito.when(productService.findProudctByProductId(1L))
        .thenReturn(ProductMapper.productRequestDtoToProduct(
            ProductRequestDto.builder()
                .productNm("Test Product")
                .productPrice(1000)
                .stockAmount(100)
                .salesRate(10)
                .discountRate(10)
                .discountPrice(900)
                .registDt(LocalDateTime.now())
                .description("Test Description")
                .deleteAt(DeleteAt.N)
                .categoryId(1L)
                .build(),
            null));

    mockMvc.perform(get("/product/detail/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productNm", is("Test Product")))
        .andExpect(jsonPath("$.price", is(1000)));
  }

  @Test
  @WithMockUser
  public void testAddProduct() throws Exception {
    ProductRequestDto requestDto = ProductRequestDto.builder()
        .productNm("New Product")
        .productPrice(2000)
        .stockAmount(50)
        .salesRate(5)
        .discountRate(20)
        .discountPrice(1600)
        .registDt(LocalDateTime.now())
        .description("New Product Description")
        .deleteAt(DeleteAt.N)
        .categoryId(1L)
        .build();

    Mockito.when(productService.changeProduct(any(ProductRequestDto.class)))
        .thenReturn(ProductMapper.productRequestDtoToProduct(requestDto,
            Category.builder().Id(1L).name("Test Category").deleteAt(DeleteAt.N).build()));

    mockMvc.perform(post("/product/add")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productNm", is("New Product")))
        .andExpect(jsonPath("$.price", is(2000)))
        .andExpect(jsonPath("$.stockQuantity", is(50)))
        .andExpect(jsonPath("$.salesRate", is(5)))
        .andExpect(jsonPath("$.discountRate", is(20)))
        .andExpect(jsonPath("$.discountPrice", is(1600)))
        .andExpect(jsonPath("$.registDt").isNotEmpty())
        .andExpect(jsonPath("$.deleteAt", is("N")))
        .andExpect(jsonPath("$.description", is("New Product Description")));
  }

  @Test
  @WithMockUser
  public void testChangeProduct() throws Exception {
    ProductRequestDto requestDto = ProductRequestDto.builder()
        .productNm("Updated Product")
        .productPrice(1500)
        .stockAmount(75)
        .salesRate(15)
        .discountRate(15)
        .discountPrice(1275)
        .registDt(LocalDateTime.now())
        .description("Updated Product Description")
        .deleteAt(DeleteAt.N)
        .categoryId(1L)
        .build();

    Mockito.when(productService.changeProduct(any(ProductRequestDto.class)))
        .thenReturn(ProductMapper.productRequestDtoToProduct(requestDto,
            Category.builder().Id(1L).name("Test Category").deleteAt(DeleteAt.N).build()));

    mockMvc.perform(put("/product/change")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productNm", is("Updated Product")))
        .andExpect(jsonPath("$.price", is(1500)))
        .andExpect(jsonPath("$.stockQuantity", is(75)))
        .andExpect(jsonPath("$.salesRate", is(15)))
        .andExpect(jsonPath("$.discountRate", is(15)))
        .andExpect(jsonPath("$.discountPrice", is(1275)))
        .andExpect(jsonPath("$.registDt").isNotEmpty())
        .andExpect(jsonPath("$.deleteAt", is("N")))
        .andExpect(jsonPath("$.description", is("Updated Product Description")));
  }

  @Test
  @WithMockUser
  public void testDeleteProduct() throws Exception {
    mockMvc.perform(delete("/product/delete/1").with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));
  }
}
