package com.shopping.web;

import com.shopping.dto.CategoryDto;
import com.shopping.mapper.CategoryMapper;
import com.shopping.service.CategoryService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/list")
  public CompletableFuture<ResponseEntity<List<CategoryDto>>> findCategoryList() {
    return categoryService.findCategoryList()
        .thenApply(categories -> ResponseEntity
            .ok(categories.stream()
                .map(CategoryMapper::categoryToCategoryeDto)
                .collect(Collectors.toList())));
  }

  @GetMapping("/detail/{categoryId}")
  public CompletableFuture<ResponseEntity<CategoryDto>> findCategoryDetail(
      @PathVariable Long categoryId) {
    return categoryService.findCategoryByCategoryId(categoryId)
        .thenApply(category -> ResponseEntity
            .ok(CategoryMapper.categoryToCategoryeDto(category)));
  }

  @PutMapping("/change")
  public CompletableFuture<ResponseEntity<CategoryDto>> changeCategory(
      @RequestBody CategoryDto categoryRequestDto) {
    return categoryService.saveCategory(categoryRequestDto)
        .thenApply(category -> ResponseEntity
            .ok(CategoryMapper.categoryToCategoryeDto(category)));
  }
}
