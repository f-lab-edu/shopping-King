package com.shopping.service;

import com.shopping.dto.CartChangeDto;
import com.shopping.entity.Cart;
import com.shopping.repository.CartRepository;
import com.shopping.mapper.CartMapper;
import com.shopping.entity.Product;
import com.shopping.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;

  public List<Cart> findCartsByMemberId(Long memberId) {
    return cartRepository.findByMemberId(memberId);
  }

  public Cart saveCart(CartChangeDto cartChangeDto) {
    Product product = productRepository
        .findById(cartChangeDto.getProductId())
        .orElseThrow(() -> new RuntimeException(
            "Product not found with id: " + cartChangeDto.getProductId()));
    return cartRepository.save(CartMapper.cartChangeDtoToCart(cartChangeDto, product));
  }
}
