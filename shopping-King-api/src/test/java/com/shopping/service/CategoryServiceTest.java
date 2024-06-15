package com.shopping.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.shopping.dto.CategoryDto;
import com.shopping.entity.Category;
import com.shopping.enums.DeleteAt;
import com.shopping.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CategoryService 테스트")
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

  @Test
  @DisplayName("모든 카테고리 조회")
  public void testFindAllCategories() throws ExecutionException, InterruptedException {
    List<Category> categories = new ArrayList<>();
    categories.add(new Category(1L, "Category 1", DeleteAt.N));
    categories.add(new Category(2L, "Category 2", DeleteAt.N));
    when(categoryRepository.findAll()).thenReturn(categories);

    CompletableFuture<List<Category>> futureCategories = categoryService.findCategoryList();
    List<Category> foundCategories = futureCategories.get();
    assertEquals(categories.size(), foundCategories.size());
  }

  @Test
  @DisplayName("특정 카테고리 조회 - 존재하는 경우")
  public void testFindCategoryByIdExists() throws ExecutionException, InterruptedException {
    Category category = new Category(1L, "Category 1", DeleteAt.N);
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

    CompletableFuture<Category> futureCategory = categoryService.findCategoryByCategoryId(1L);
    Category foundCategory = futureCategory.get();

    assertEquals(category, foundCategory);
  }

  @Test
  @DisplayName("특정 카테고리 조회 - 존재하지 않는 경우")
  public void testFindCategoryByIdNotExists() {
    when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

    CompletableFuture<Category> futureCategory = categoryService.findCategoryByCategoryId(1L);

    ExecutionException exception = assertThrows(ExecutionException.class, () -> {
      futureCategory.get();
    });

    assertEquals(EntityNotFoundException.class, exception.getCause().getClass());
  }

  @Test
  @DisplayName("카테고리 저장")
  public void testSaveCategory() throws ExecutionException, InterruptedException {
    CategoryDto categoryDto = new CategoryDto(1L, "Category 1", DeleteAt.N);
    Category category = new Category(1L, "Category 1", DeleteAt.N);
    when(categoryRepository.save(any())).thenReturn(category);

    CompletableFuture<Category> futureCategory = categoryService.saveCategory(categoryDto);
    Category savedCategory = futureCategory.get();

    assertEquals(category, savedCategory);
  }
}
