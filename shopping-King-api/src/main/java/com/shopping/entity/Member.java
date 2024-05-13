package com.shopping.entity;

import java.util.ArrayList;
import java.util.List;
import com.shopping.enums.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String name;

  private String password;

  private String address;

  @Enumerated(value = EnumType.STRING)
  private MemberRole memberRole;

  @Builder.Default
  @OneToMany(mappedBy = "member")
  private List<Orders> orders = new ArrayList<Orders>();
}
