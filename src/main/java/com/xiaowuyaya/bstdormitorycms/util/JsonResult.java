package com.xiaowuyaya.bstdormitorycms.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 返回结果集
 */
@Data
@AllArgsConstructor
public class JsonResult {

    /**
     * 20000 success;
     * 50008: Illegal token;
     * 50012: Other clients logged in;
     * 50014: Token expired;
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;


    public static JsonResult success(Object data, String msg) {
        return new JsonResult(20000, msg, data);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(20000, "", data);
    }

    public static JsonResult failed(Object data, String msg) {
        return new JsonResult(10000, msg, data);
    }

    public static JsonResult failed(Object data) {
        return new JsonResult(10000, "", data);
    }

    public static JsonResult jsonResult(Integer code, String msg, Object data) {
        return new JsonResult(code, msg, data);
    }
}
