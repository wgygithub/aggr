package org.example.exceptions;

import org.example.enums.ResultType;

/**
 * 幂等，重复请求
 */
public class RepeatCallException extends AppException {
    public RepeatCallException() {
        super(ResultType.REPEATCALL_ERROR);
    }

    public RepeatCallException(String msg) {
        super(ResultType.REPEATCALL_ERROR, msg);
    }
}
