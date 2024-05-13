package com.shopping.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dto.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private ProductResponseDto testProduct;

  @BeforeEach
  public void setup() {
    testProduct = ProductResponseDto
        .builder()
        .productId(1L)
        .productName("Test Product")
        .stockQuantity(100)
        .salesRate(50)
        .category("Test Category")
        .price(10000)
        .discountRate(10)
        .description("Test Description")
        .build();
  }

  @Test
  public void testProductList() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/product/list"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("[{\"productName\":\"Test Product\"}]"))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testProductDetail() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/product/detail/{productId}", testProduct.getProductId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Test Product"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.stockQuantity").value(100))
        .andExpect(MockMvcResultMatchers.jsonPath("$.salesRate").value(50))
        .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Test Category"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10000))
        .andExpect(MockMvcResultMatchers.jsonPath("$.discountRate").value(10))
        .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("SALE"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Description"))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testInsertProduct() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/product/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testProduct)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.header().exists("Location"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Test Product"))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testUpdateProduct() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders
            .put("/product/change")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testProduct)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().exists("Location"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Test Product"))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testDeleteProduct() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/product/delete/{productId}", testProduct.getProductId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(testProduct.getProductId().toString()))
        .andDo(MockMvcResultHandlers.print());
  }
}
