package com.shopping.aop;

import com.shopping.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginAspect {

/*  private final HttpServletRequest request;
  private final RedisUtil redisUtil;*/

/*  @Around("execution(* com.shopping.web..*(..))")
  public Object authenticate(ProceedingJoinPoint joinPoint) throws Throwable {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new RuntimeException("Unauthorized access");
    }

    String token = authorizationHeader.substring(7);
    if (!redisUtil.validateToken(token)) {
      throw new RuntimeException("Unauthorized access");
    }

    return joinPoint.proceed();
  }*/
}
