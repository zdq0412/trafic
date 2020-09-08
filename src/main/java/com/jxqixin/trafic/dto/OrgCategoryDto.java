package com.jxqixin.trafic.dto;

/**
 * 企业类别dto
 */
public class OrgCategoryDto {

    private String id;

    private String name;

    private String note;
    /**删除标识*/
    private boolean deleted;
    /**操作类型：停用或启用 play or pause*/
    private String operType;
    /**
     * 安全生产费用提取百分比
     */
    private float safetyCostRatio;

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public float getSafetyCostRatio() {
        return safetyCostRatio;
    }

    public void setSafetyCostRatio(float safetyCostRatio) {
        this.safetyCostRatio = safetyCostRatio;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
