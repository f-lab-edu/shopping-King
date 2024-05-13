package com.shopping.dto;

import com.shopping.enums.MemberRole;
import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {

  @Nullable
  private Long id;

  private String name;

  private String password;

  private String address;

  private MemberRole memberRole;

}
