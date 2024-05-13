package com.shopping.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shopping.dto.CategoryDto;
import com.shopping.entity.Category;
import com.shopping.repository.CategoryRepository;

import com.shopping.service.CategoryService;
import com.shopping.enums.DeleteAt;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayName("CategoryService 테스트")
public class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("모든 카테고리 조회")
  public void testFindAllCategories() {
    List<Category> categories = new ArrayList<>();
    categories.add(new Category(1L, "Category 1", DeleteAt.N));
    categories.add(new Category(2L, "Category 2", DeleteAt.N));
    when(categoryRepository.findAll()).thenReturn(categories);

    List<Category> foundCategories = categoryService.findCategoryList();

    assertEquals(categories.size(), foundCategories.size());
  }

  @Test
  @DisplayName("특정 카테고리 조회 - 존재하는 경우")
  public void testFindCategoryByIdExists() {
    Category category = new Category(1L, "Category 1", DeleteAt.N);
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

    Category foundCategory = categoryService.findCategoryByCategoryId(1L);

    assertEquals(category, foundCategory);
  }

  @Test
  @DisplayName("특정 카테고리 조회 - 존재하지 않는 경우")
  public void testFindCategoryByIdNotExists() {
    when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> {
      categoryService.findCategoryByCategoryId(1L);
    });
  }

  @Test
  @DisplayName("카테고리 저장")
  public void testSaveCategory() {
    CategoryDto categoryDto = new CategoryDto(1L, "Category 1", DeleteAt.N);
    Category category = new Category(1L, "Category 1", DeleteAt.N);
    when(categoryRepository.save(any())).thenReturn(category);

    Category savedCategory = categoryService.saveCategory(categoryDto);

    assertEquals(category, savedCategory);
  }
}
