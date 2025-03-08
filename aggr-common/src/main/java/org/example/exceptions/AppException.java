package org.example.exceptions;

import org.example.enums.ResultType;

public abstract class AppException extends RuntimeException {

    private final ResultType resultType;
    private String msg;

    public AppException(ResultType resultType) {
        super(resultType.getMsg());
        this.resultType = resultType;
    }

    public AppException(ResultType resultType, String msg) {
        super(msg);
        this.msg = msg;
        this.resultType = resultType;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public String getMsg() {
        return msg;
    }
}
