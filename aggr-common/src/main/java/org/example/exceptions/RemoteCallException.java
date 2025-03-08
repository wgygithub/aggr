package org.example.exceptions;

import org.example.enums.ResultType;

public class RemoteCallException extends AppException {

    public RemoteCallException() {
        super(ResultType.REMOTECALL_ERROR);
    }

    public RemoteCallException(String msg) {
        super(ResultType.REMOTECALL_ERROR, msg);
    }

}
