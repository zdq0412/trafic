package com.jxqixin.trafic.dto;
/**
 * 存放分页相关属性
 */
public class PageDto {
    /**第几页*/
    private Integer page;
    /**每页显示条数*/
    private Integer limit;
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page-1;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
