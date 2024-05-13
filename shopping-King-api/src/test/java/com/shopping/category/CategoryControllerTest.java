package com.shopping.category;

import static org.hamcrest.Matchers.is;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dto.CategoryDto;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private CategoryDto testCategory;

  @BeforeEach
  public void setup() {
  }

  @Test
  public void testCategoryList() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/category/list"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].categoryId", is(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", is("Sample Category")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].status", is("USE")));
  }

  @Test
  public void testCategoryDetail() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/category/detail/{categoryId}", testCategory.getCategoryId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId", is(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Sample Category")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("USE")));
  }

  @Test
  public void testUpdateCategory() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders
            .put("/category/change")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testCategory)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().exists("Location"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Test Category")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("USE")));
  }

  @Test
  public void testDeleteCategory() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/category/delete/{categoryId}", testCategory.getCategoryId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(testCategory.getCategoryId().toString()))
        .andDo(MockMvcResultHandlers.print());
  }
}
