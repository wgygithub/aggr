package org.example.exceptions;

import org.example.enums.ResultType;

public class TokenExpiredException extends AppException{
  public TokenExpiredException() {
    super(ResultType.HEADERPARAM_ERROR);
  }

  public TokenExpiredException(String msg) {
    super(ResultType.HEADERPARAM_ERROR, msg);
  }
}
