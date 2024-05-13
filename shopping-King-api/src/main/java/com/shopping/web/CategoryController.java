package com.shopping.web;

import com.shopping.dto.CategoryDto;
import com.shopping.service.CategoryService;
import com.shopping.mapper.CategoryMapper;
import java.util.List;
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
  public ResponseEntity<List<CategoryDto>> findCategoryList() {
    return ResponseEntity
        .ok(categoryService
            .findCategoryList()
            .stream()
            .map(CategoryMapper::categoryToCategoryeDto)
            .collect(Collectors.toList()));
  }

  @GetMapping("/detail/{categoryId}")
  public ResponseEntity<CategoryDto> findCategoryDetail(@PathVariable Long categoryId) {
    return ResponseEntity
        .ok(CategoryMapper
            .categoryToCategoryeDto(categoryService.findCategoryByCategoryId(categoryId)));
  }

  @PutMapping("/change")
  public ResponseEntity<CategoryDto> changeCategory(@RequestBody CategoryDto categoryRequestDto) {
    return ResponseEntity
        .ok(CategoryMapper
            .categoryToCategoryeDto(categoryService.saveCategory(categoryRequestDto)));
  }
  // @DeleteMapping("/delete/{categoryId}")
  // public ResponseEntity<Long> deleteCategory(@PathVariable Long categoryId) {
  // return ResponseEntity.ok(categoryId);
  // }
}
