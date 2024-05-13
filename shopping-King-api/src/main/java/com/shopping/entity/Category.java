package com.shopping.entity;

import com.shopping.enums.DeleteAt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

  @Id
  @GeneratedValue
  @Column(name = "category_id")
  private Long Id;

  @Column(name = "category_nm")
  private String name;

  @Column(name = "category_stats")
  @Enumerated(EnumType.STRING)
  private DeleteAt deleteAt;

}
