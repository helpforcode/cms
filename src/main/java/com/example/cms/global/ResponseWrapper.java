package com.example.cms.global;

import lombok.Data;


@Data
public class ResponseWrapper {
    private Integer code;
    private String message;
    private Object data;
    public static final Integer SUCCEED = 200;
    public static final Integer FAILED = 500;

    public ResponseWrapper(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseWrapper failed(String message, Object data) {
        return new ResponseWrapper(FAILED, message, data);
    }

    public static ResponseWrapper succeed(Object data) {
        return new ResponseWrapper(SUCCEED, "", data);
    }

    public static <T> ResponseWrapper succeed(String message, Object data) {
        return new ResponseWrapper(SUCCEED, message, data);
    }
}
