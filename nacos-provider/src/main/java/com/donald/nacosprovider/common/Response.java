package com.donald.nacosconsumer.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author donald
 * @date 2022/05/05
 */
@Slf4j
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Response<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    private Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 通用异常的返回错误码和错误信息
     */
    public static <T> Response<T> exception(int code) {
        if (log.isDebugEnabled()) {

            return new Response<>(code, "");
        } else {

            return new Response<>(code, "");
        }
    }

    /**
     * 返回成功信息
     */
    public static <T> Response<T> success() {
        return new Response<>(0, "");
    }

    /**
     * 返回成功信息（指定业务码进行国际化）
     */
    public static <T> Response<T> success(int code) {
        return new Response<>(code, "", null);
    }

    /**
     * 返回成功信息，并返回数据
     */
    public static <T> Response<T> success(T data) {
        return new Response<>(0, "", data);
    }

    /**
     * 返回成功信息，并返回数据（指定业务码进行国际化）
     */
    public static <T> Response<T> success(int code, Object... args) {

        return new Response<>(code, "");
    }
}
