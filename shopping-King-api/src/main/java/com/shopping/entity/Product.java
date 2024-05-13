package com.shopping.entity;

import com.shopping.enums.DeleteAt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

  @Id
  @GeneratedValue
  @Column(name = "product_id")
  private Long id;

  @Column(name = "product_nm")
  private String productNm;

  @Column(name = "product_price")
  private int productPrice;

  @Column(name = "stock_amount")
  private int stockAmount;

  @Column(name = "sales_rate")
  private int salesRate;

  @Column(name = "discount_rate")
  private int discountRate;

  @Column(name = "discount_price")
  private int discountPrice;

  @Column(name = "product_regist_dt")
  private LocalDateTime registDt;

  @Lob
  @Column(name = "product_dc")
  private String description;

  @Column(name = "delete_at")
  @Enumerated(EnumType.STRING)
  private DeleteAt deleteAt;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
