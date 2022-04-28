package com.donald.internalapisecurity.config;

/**
 * @author donald
 * @date 2022/01/12
 */
public class InternalApiException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String msg;

    public InternalApiException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}