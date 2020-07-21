package com.jxqixin.trafic.dto;

import java.io.Serializable;
/**
 * 法律法规文件
 */
public class LawDto implements Serializable {
    private String id;
    /**名称*/
    private String name;
    /**法律法规文件内容*/
    private String content;
    /**发布日期*/
    private String publishDate;
    /**实施日期*/
    private String implementDate;
    /**发文部门名称*/
    private String publishDepartment;
    /**备注*/
    private String note;
    /**所属省*/
    private String provinceId;
    /**所属市*/
    private String cityId;
    /**所属地区*/
    private String regionId;
    /**企业类别名称*/
    private String orgCategoryId;
    /**发文字号：企业简称+年月日+自增序号*/
    private String num;
    /**时效性：有效或无效*/
    private String timeliness;

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getImplementDate() {
        return implementDate;
    }

    public void setImplementDate(String implementDate) {
        this.implementDate = implementDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishDepartment() {
        return publishDepartment;
    }

    public void setPublishDepartment(String publishDepartment) {
        this.publishDepartment = publishDepartment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getOrgCategoryId() {
        return orgCategoryId;
    }

    public void setOrgCategoryId(String orgCategoryId) {
        this.orgCategoryId = orgCategoryId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(String timeliness) {
        this.timeliness = timeliness;
    }
}
