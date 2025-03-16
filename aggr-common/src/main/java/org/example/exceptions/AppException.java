package org.example.exceptions;

import lombok.Getter;
import org.example.enums.ResultType;

@Getter
public class AppException extends RuntimeException {

    private final ResultType resultType;
    private String msg;

    public AppException(String msg) {
        this(ResultType.BUSINESS_ERROR, msg);
    }

    public AppException(ResultType resultType) {
        super(resultType.getMsg());
        this.resultType = resultType;
    }

    public AppException(ResultType resultType, String msg) {
        super(msg);
        this.msg = msg;
        this.resultType = resultType;
    }

}
