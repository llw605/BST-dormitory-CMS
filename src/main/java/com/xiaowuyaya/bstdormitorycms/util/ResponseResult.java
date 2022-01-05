package com.xiaowuyaya.bstdormitorycms.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author xiaowuyaya
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 查询到的结果数据，
     */
    private T data;

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }


    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseResult<T> success(String msg, T data){
        return new ResponseResult<>(20000,msg,data);
    }

    public static <T> ResponseResult<T> success(String msg){
        return new ResponseResult<>(20000,msg);
    }

    public static <T> ResponseResult<T> success(T data){
        return new ResponseResult<>(20000,data);
    }

    public static <T> ResponseResult<T> fail(String msg, T data){
        return new ResponseResult<>(10000,msg,data);
    }

    public static <T> ResponseResult<T> fail(String msg){
        return new ResponseResult<>(10000,msg);
    }

}