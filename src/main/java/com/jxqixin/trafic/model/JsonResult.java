package com.jxqixin.trafic.model;

import com.jxqixin.trafic.constant.Result;

import java.io.Serializable;
/**
 * 统一返回json结果类
 */
public class JsonResult<T> implements Serializable {
    /**返回数据*/
    private T data;
    /**返回码和消息对象*/
    private Result result;

    public JsonResult(Result result){
        this.result = result;
    }
    public JsonResult(Result result,T data){
        this(result);
        this.data = data;
    }

    public JsonResult(T data){
        this.data = data;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
