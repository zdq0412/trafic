package com.jxqixin.trafic.dto;
import java.io.Serializable;
/**
 * 企业发文通知
 */
public class NoticeDto implements Serializable {
    private String id;
    /**名称*/
    private String name;
    /**文件内容*/
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
    private String province;
    /**所属市*/
    private String city;
    /**所属地区*/
    private String region;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
