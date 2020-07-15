package com.jxqixin.trafic.interceptors;

/***
 * 不拦截的路径类
 */
public class InterceptorExcept{
    private String method;
    private String url;
    public InterceptorExcept(String method,String url){
        this.method = method;
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
