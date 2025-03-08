package org.example.resp;


import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import org.example.enums.ResultType;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> OK() {
        Result<T> result = new Result<>();
        result.setCode(ResultType.SUCCESS.getCode());
        result.setMessage(ResultType.SUCCESS.getMsg());
        return result;
    }

    public static <T> Result<T> OK(T data) {
        Result<T> result = Result.OK();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(ResultType type) {
        Result<T> result = new Result<>();
        result.setCode(type.getCode());
        result.setMessage(type.getMsg());
        return result;
    }

    public static <T> Result<T> fail(ResultType type, String msg) {
        Result<T> result = fail(type);
        msg = ObjectUtil.isEmpty(msg) ? type.getMsg() : msg;
        result.setMessage(msg);
        return result;
    }

    public static <T> Result<T> fail(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultType.FAIL.getCode());
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> fail(ResultType type, T resp) {
        Result<T> result = fail(type);
        result.setData(resp);
        return result;
    }
}
