package org.example.exceptions;

import org.example.enums.ResultType;

/**
 * @author admin
 */
public class LockAcquireException extends AppException {
  public LockAcquireException(String msg) {
    super(ResultType.LOCK_ACQ_ERROR, msg);
  }
}
