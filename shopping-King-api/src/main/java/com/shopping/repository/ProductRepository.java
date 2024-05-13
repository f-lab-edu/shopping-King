package com.shopping.repository;

import com.shopping.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findAllByCategoryId(Long categoryId);
}
