package org.example.exceptions.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.ResultType;
import org.example.exceptions.AppException;
import org.example.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@RestControllerAdvice
public class AppExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(AppException.class)
    public Result<Void> appException(AppException appException) {
        ResultType errorType = appException.getResultType();
        String msg = appException.getMsg();
        log.error("uri: {} app error:{} ex:", request.getRequestURI(),
                errorType.name(), appException);
        return Result.fail(errorType, msg);
    }
}
