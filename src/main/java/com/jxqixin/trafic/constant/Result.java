package com.jxqixin.trafic.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 返回值成功或失败返回码和返回信息
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Result{
    SUCCESS(200,"操作成功!"),
    FAIL(300,"操作失败!"),
    USER_NOT_LOGIN(400,"用户未登陆!"),
    LOGIN_TIMEOUT(600,"登录超时!");
    /**返回码*/
    private int resultCode;
    /**返回信息*/
    private String message;

    Result(int resultCode,String message){
        this.resultCode = resultCode;
        this.message = message;
    }


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
