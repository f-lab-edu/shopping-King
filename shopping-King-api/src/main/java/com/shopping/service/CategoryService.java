package com.shopping.service;

import com.shopping.dto.CategoryDto;
import com.shopping.entity.Category;
import com.shopping.repository.CategoryRepository;
import com.shopping.mapper.CategoryMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public List<Category> findCategoryList() {
    return categoryRepository.findAll();
  }

  public Category findCategoryByCategoryId(Long categoryId) {
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(
            () -> new EntityNotFoundException("Category not found with id: " + categoryId));
  }

  public Category saveCategory(CategoryDto categoryDto) {
    return categoryRepository.save(CategoryMapper.categoryDtoToCategory(categoryDto));
  }
}
