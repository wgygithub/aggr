package org.example.exceptions.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.ResultType;
import org.example.exceptions.AppException;
import org.example.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(AppException.class)
    public Result<Void> appException(AppException appException) {
        ResultType errorType = appException.getResultType();
        String msg = appException.getMsg();
        log.error("uri: {} app error:{} ex:", request.getRequestURI(), errorType.name(), appException);
        return Result.fail(errorType, msg);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("uri: {} app error:{} ex:", request.getRequestURI(), ResultType.PARAM_ERROR.name(), ex);
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("fail :");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(", ");
        }

        return Result.fail(ResultType.PARAM_ERROR, sb.toString());
    }

    @ExceptionHandler({MissingRequestValueException.class})
    public Result<Void> handleMissingRequestValueException(MethodArgumentNotValidException ex) {
        log.error("uri: {} app error:{} ex:", request.getRequestURI(), ResultType.PARAM_ERROR.name(), ex);
        String errorMessage = ex.getMessage();
        return Result.fail(ResultType.PARAM_ERROR, errorMessage);
    }


}
