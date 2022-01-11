package com.donald.nacosconsumer.config;

/**
 * @author donald
 * @date 2022/01/10
 */
public class ExceptionResponse {
    private String message;
    private Object extendedData;
    private String errorField;
    private String detailInfo;
    private int code;
    private String msg;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getExtendedData() {
        return extendedData;
    }

    public void setExtendedData(Object extendedData) {
        this.extendedData = extendedData;
    }

    public String getErrorField() {
        return errorField;
    }

    public void setErrorField(String errorField) {
        this.errorField = errorField;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
