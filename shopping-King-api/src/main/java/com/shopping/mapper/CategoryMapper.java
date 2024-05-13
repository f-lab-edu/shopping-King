package com.shopping.mapper;

import com.shopping.dto.CategoryDto;
import com.shopping.entity.Category;

public class CategoryMapper {

  public static CategoryDto categoryToCategoryeDto(Category category) {
    return category == null ? null
        : CategoryDto
            .builder()
            .categoryId(category.getId())
            .name(category.getName())
            .deleteAt(category.getDeleteAt())
            .build();
  }

  public static Category categoryDtoToCategory(CategoryDto categoryDto) {
    return categoryDto == null ? null
        : Category
            .builder()
            .Id(categoryDto.getCategoryId())
            .name(categoryDto.getName())
            .deleteAt(categoryDto.getDeleteAt())
            .build();
  }
}
