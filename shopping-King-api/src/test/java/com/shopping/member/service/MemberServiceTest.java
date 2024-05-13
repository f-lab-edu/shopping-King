package com.shopping.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.shopping.dto.MemberRequestDto;
import com.shopping.entity.Member;
import com.shopping.repository.MemberRepository;
import com.shopping.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayName("MemberService 테스트")
public class MemberServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private MemberService memberService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("회원 가입 - 실패하는 테스트")
  public void testRegisterMember_Failure() {
    // Given
    MemberRequestDto requestDto = MemberRequestDto.builder()
        .name("John")
        .password("password")
        .address("123 Street")
        .build();
    // When
    when(memberRepository.save(any())).thenThrow(new RuntimeException("Failed to save member"));
    // Then
    assertThrows(RuntimeException.class, () -> {
      memberService.addMember(requestDto);
    });
  }

  @Test
  @DisplayName("회원 추가 - 성공하는 테스트")
  public void testAddMember_Success() {
    // Given
    MemberRequestDto requestDto = MemberRequestDto.builder()
        .name("John")
        .password("password")
        .address("123 Street")
        .build();
    Member savedMember = Member.builder()
        .id(1L)
        .name("John")
        .password("password")
        .address("123 Street")
        .build();

    // When
    when(memberRepository.save(any())).thenReturn(savedMember);
    Member addedMember = memberService.addMember(requestDto);

    // Then
    assertEquals(savedMember, addedMember);
    verify(memberRepository).save(any());
  }
}
