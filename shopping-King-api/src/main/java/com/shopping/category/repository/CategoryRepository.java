package com.shopping.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
