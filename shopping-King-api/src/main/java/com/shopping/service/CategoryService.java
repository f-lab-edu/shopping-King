package com.shopping.service;

import com.shopping.dto.CategoryDto;
import com.shopping.entity.Category;
import com.shopping.mapper.CategoryMapper;
import com.shopping.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CompletableFuture<List<Category>> findCategoryList() {
    return CompletableFuture.supplyAsync(() -> categoryRepository.findAll());
  }

  public CompletableFuture<Category> findCategoryByCategoryId(Long categoryId) {
    return CompletableFuture.supplyAsync(() -> categoryRepository.findById(categoryId)
        .orElseThrow(
            () -> new EntityNotFoundException("Category not found with id: " + categoryId)));
  }

  public CompletableFuture<Category> saveCategory(CategoryDto categoryDto) {
    return CompletableFuture.supplyAsync(
        () -> categoryRepository.save(CategoryMapper.categoryDtoToCategory(categoryDto)));
  }
}
