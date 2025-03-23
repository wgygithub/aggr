package org.example.enums;

import lombok.Getter;

@Getter
public enum ResultType {
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    //业务错误
    BUSINESS_ERROR(300, "BUSINESS_ERROR"),
    //参数错误
    PARAM_ERROR(400, "PARAM_ERROR"),
    //json错误
    JSON_ERROR(401, "JSON_ERROR"),
    //重复调用，幂等
    REPEATCALL_ERROR(402, "REPEATCALL_ERROR"),
    //频率限制，限制调用次数
    RATELIMIT_ERROR(403, "RATELIMIT_ERROR"),
    //远程调用失败
    REMOTECALL_ERROR(408, "REMOTECALL_ERROR"),
    //header参数错误
    HEADERPARAM_ERROR(204,"header param miss"),
    //token过期
    TOKENEXPIRED_ERROR(405,"token expired"),
    //认证失败
    AUTH_FAIL(405, "AUTH_FAIL");

    ResultType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;
}
