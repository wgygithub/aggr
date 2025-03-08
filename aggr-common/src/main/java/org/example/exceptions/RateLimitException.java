package org.example.exceptions;

import org.example.enums.ResultType;

public class RateLimitException extends AppException {
    public RateLimitException() {
        super(ResultType.RATELIMIT_ERROR);
    }

    public RateLimitException(String msg) {
        super(ResultType.RATELIMIT_ERROR, msg);
    }
}
