package com.shopping.service;

import com.shopping.mapper.MemberMapper;
import com.shopping.dto.MemberRequestDto;
import com.shopping.entity.Member;
import com.shopping.repository.MemberRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public Member addMember(MemberRequestDto requestMemberDto) {
    return memberRepository.save(
        Objects.requireNonNull(MemberMapper.requestMemberDtoToMemberForSave(requestMemberDto)));
  }

  public boolean existsByName(String existingName) {
    return memberRepository.existsByName(existingName);
  }


}
