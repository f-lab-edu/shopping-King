package com.shopping.repository;

import com.shopping.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Boolean existsByName(String memberName);

  Member findByname(String name);
}
