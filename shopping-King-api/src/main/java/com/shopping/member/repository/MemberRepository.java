package com.shopping.member.repository;

import com.shopping.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Boolean existsByName(String memberName);

  Member findByname(String name);
}
