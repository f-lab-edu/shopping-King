package com.shopping.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shopping.dto.MemberLoginRequestDto;
import com.shopping.dto.MemberRequestDto;
import com.shopping.entity.Member;
import com.shopping.enums.MemberRole;
import com.shopping.repository.MemberRepository;
import com.shopping.util.JwtTokenUtil;
import com.shopping.util.RedisUtil;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("MemberService 테스트")
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private MemberService memberService;

  @Mock
  private JwtTokenUtil jwtTokenUtil;

  @Mock
  private RedisUtil redisUtil;


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

  @Test
  @DisplayName("로그인 성공")
  void testLoginSuccess() {
    // 가짜 데이터 설정
    MemberLoginRequestDto loginRequestDto = new MemberLoginRequestDto("testUser", "password");
    Member member = Member.builder()
        .id(1L)
        .name("testUser")
        .password("password")
        .mail("test@test.com")
        .address("Test Address")
        .memberRole(MemberRole.USER)
        .build();

    // Mock 설정
    when(memberRepository.findByName("testUser")).thenReturn(Optional.of(member));
    when(jwtTokenUtil.createToken("testUser", MemberRole.USER.name())).thenReturn("testToken");

    // 로그인 시도
    String token = memberService.login(loginRequestDto);

    // 결과 검증
    assertEquals("testToken", token);
  }

  @Test
  @DisplayName("로그인 실패 - 회원이 존재하지 않는 경우")
  void testLoginFailure_MemberNotExist() {
    // 가짜 데이터 설정
    MemberLoginRequestDto loginRequestDto = new MemberLoginRequestDto("testUser", "password");

    // Mock 설정
    when(memberRepository.findByName("testUser")).thenReturn(Optional.empty());

    // 로그인 시도
    assertThrows(RuntimeException.class, () -> memberService.login(loginRequestDto));
  }

  @Test
  @DisplayName("로그인 실패 - 비밀번호가 일치하지 않는 경우")
  void testLoginFailure_WrongPassword() {
    // 가짜 데이터 설정
    MemberLoginRequestDto loginRequestDto = new MemberLoginRequestDto("testUser", "wrongPassword");
    Member member = Member.builder()
        .id(1L)
        .name("testUser")
        .password("password")
        .mail("test@test.com")
        .address("Test Address")
        .memberRole(MemberRole.USER)
        .build();

    // Mock 설정
    when(memberRepository.findByName("testUser")).thenReturn(Optional.of(member));

    // 로그인 시도
    assertThrows(RuntimeException.class, () -> memberService.login(loginRequestDto));
  }
}
