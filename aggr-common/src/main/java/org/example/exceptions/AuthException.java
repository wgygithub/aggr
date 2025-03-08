package org.example.exceptions;

import org.example.enums.ResultType;

public class AuthException extends AppException {

    public AuthException() {
        super(ResultType.AUTH_FAIL);
    }

    public AuthException(String msg) {
        super(ResultType.AUTH_FAIL, msg);
    }
}
