package com.shopping.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dto.CategoryDto;
import com.shopping.entity.Category;
import com.shopping.enums.DeleteAt;
import com.shopping.service.CategoryService;
import com.shopping.web.CategoryController;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Mock
  private CategoryService categoryService;

  @InjectMocks
  private CategoryController categoryController;

  private CategoryDto testCategoryDto;
  private Category testCategory;

  @BeforeEach
  public void setup() {
    testCategory = new Category(1L, "Category 1", DeleteAt.N);
    testCategoryDto = new CategoryDto(1L, "Category 1", DeleteAt.N);
  }

  @Test
  public void testCategoryList() throws Exception {
    List<Category> categories = Arrays.asList(testCategory);
    when(categoryService.findCategoryList()).thenReturn(
        CompletableFuture.completedFuture(categories));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/category/list"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].categoryId", is(1)))
        .andExpect(jsonPath("$.[0].name", is("Category 1")))
        .andExpect(jsonPath("$.[0].deleteAt", is("N")))
        .andDo(print());
  }

  @Test
  public void testCategoryDetail() throws Exception {
    when(categoryService.findCategoryByCategoryId(1L)).thenReturn(
        CompletableFuture.completedFuture(testCategory));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/category/detail/{categoryId}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.categoryId", is(1)))
        .andExpect(jsonPath("$.name", is("Category 1")))
        .andExpect(jsonPath("$.deleteAt", is("N")))
        .andDo(print());
  }

  @Test
  public void testUpdateCategory() throws Exception {
    when(categoryService.saveCategory(testCategoryDto)).thenReturn(
        CompletableFuture.completedFuture(testCategory));

    mockMvc
        .perform(MockMvcRequestBuilders
            .put("/category/change")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testCategoryDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.categoryId", is(1)))
        .andExpect(jsonPath("$.name", is("Category 1")))
        .andExpect(jsonPath("$.deleteAt", is("N")))
        .andDo(print());
  }
}
