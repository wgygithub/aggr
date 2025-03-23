package org.example.exceptions;

import org.example.enums.ResultType;

public class HeaderParamException extends AppException{
  public HeaderParamException() {
    super(ResultType.HEADERPARAM_ERROR);
  }

  public HeaderParamException(String msg) {
    super(ResultType.HEADERPARAM_ERROR, msg);
  }

}
