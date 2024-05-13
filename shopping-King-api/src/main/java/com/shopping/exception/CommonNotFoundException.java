package com.shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommonNotFoundException extends RuntimeException {

  public CommonNotFoundException(Long id) {
    super("Not found with id:" + id);
  }
}
