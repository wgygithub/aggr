package org.example.exceptions;

import org.example.enums.ResultType;

public class ParamException extends AppException {

    public ParamException() {
        super(ResultType.PARAM_ERROR);
    }

    public ParamException(String msg) {
        super(ResultType.PARAM_ERROR, msg);
    }

}
