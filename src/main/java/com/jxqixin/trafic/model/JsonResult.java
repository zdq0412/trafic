package com.jxqixin.trafic.model;
import com.jxqixin.trafic.constant.Result;

import java.io.Serializable;
/**
 * 统一返回json结果类
 */
public class JsonResult<T> implements Serializable {
    /**操作是否成功*/
    private Boolean success;
    /**返回码*/
    private Integer resultCode;
    /**错误消息*/
    private String resultMsg;
    /**返回数据*/
    private T data;

    public JsonResult(Boolean success) {
        this.success = success;
        if(success!=null){
            this.resultCode = success? Result.SUCCESS.getResultCode():Result.FAIL.getResultCode();
            this.resultMsg = success ? Result.SUCCESS.getMessage() : Result.FAIL.getMessage();
        }
    }
    public JsonResult(Boolean success,  String resultMsg) {
        this.success = success;
        if(success!=null){
            this.resultCode = success? Result.SUCCESS.getResultCode():Result.FAIL.getResultCode();
        }
        this.resultMsg = resultMsg;
    }
    public JsonResult(Boolean success,  String resultMsg,T data) {
        this.success = success;
        if(success!=null){
            this.resultCode = success? Result.SUCCESS.getResultCode():Result.FAIL.getResultCode();
        }
        this.resultMsg = resultMsg;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getresultMsg() {
        return resultMsg;
    }

    public void setresultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
