package com.shopping.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dto.CategoryDto;
import com.shopping.enums.DeleteAt;
import com.shopping.mapper.CategoryMapper;
import com.shopping.service.CategoryService;
import com.shopping.web.CategoryController;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CategoryService categoryService;

  @Autowired
  private ObjectMapper objectMapper ;

  @Test
  @WithMockUser
  void testFindCategoryList() throws Exception {
    CategoryDto categoryDto1 = new CategoryDto(1L, "Electronics", DeleteAt.N);
    CategoryDto categoryDto2 = new CategoryDto(2L, "Books", DeleteAt.N);
    List<CategoryDto> categoryDtoList = Arrays.asList(categoryDto1, categoryDto2);

    Mockito.when(categoryService.findCategoryList())
        .thenReturn(Arrays.asList(CategoryMapper.categoryDtoToCategory(categoryDto1),
            CategoryMapper.categoryDtoToCategory(categoryDto2)));

    mockMvc.perform(get("/category/list"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].categoryId").value(categoryDto1.getCategoryId()))
        .andExpect(jsonPath("$[0].name").value(categoryDto1.getName()))
        .andExpect(jsonPath("$[0].deleteAt").value(categoryDto1.getDeleteAt().toString()))
        .andExpect(jsonPath("$[1].categoryId").value(categoryDto2.getCategoryId()))
        .andExpect(jsonPath("$[1].name").value(categoryDto2.getName()))
        .andExpect(jsonPath("$[1].deleteAt").value(categoryDto2.getDeleteAt().toString()));
  }

  @Test
  @WithMockUser
  void testFindCategoryDetail() throws Exception {
    CategoryDto categoryDto = new CategoryDto(1L, "Electronics", DeleteAt.N);

    Mockito.when(categoryService.findCategoryByCategoryId(anyLong()))
        .thenReturn(CategoryMapper.categoryDtoToCategory(categoryDto));

    mockMvc.perform(get("/category/detail/{categoryId}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.categoryId").value(categoryDto.getCategoryId()))
        .andExpect(jsonPath("$.name").value(categoryDto.getName()))
        .andExpect(jsonPath("$.deleteAt").value(categoryDto.getDeleteAt().toString()));
  }

  @Test
  @WithMockUser
  void testChangeCategory() throws Exception {
    CategoryDto categoryDto = new CategoryDto(1L, "Electronics", DeleteAt.N);

    Mockito.when(categoryService.saveCategory(any(CategoryDto.class)))
        .thenReturn(CategoryMapper.categoryDtoToCategory(categoryDto));

    mockMvc.perform(put("/category/change")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(categoryDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.categoryId").value(categoryDto.getCategoryId()))
        .andExpect(jsonPath("$.name").value(categoryDto.getName()))
        .andExpect(jsonPath("$.deleteAt").value(categoryDto.getDeleteAt().toString()));
  }
}
