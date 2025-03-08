package org.example.exceptions;

import org.example.enums.ResultType;

public class JacksonJsonException extends AppException {
    public JacksonJsonException() {
        super(ResultType.JSON_ERROR);
    }

    public JacksonJsonException(String msg) {
        super(ResultType.JSON_ERROR, msg);
    }
}
