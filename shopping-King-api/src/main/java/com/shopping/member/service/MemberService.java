package com.shopping.member.service;

import com.shopping.common.mapper.MemberMapper;
import com.shopping.member.dto.MemberRequestDto;
import com.shopping.member.entity.Member;
import com.shopping.member.repository.MemberRepository;
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
