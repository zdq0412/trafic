package com.jxqixin.trafic.dto;

/**
 * 企业类别dto
 */
public class OrgCategoryDto {

    private String id;

    private String name;

    private String note;
    /**
     * 安全生产费用提取百分比
     */
    private float safetyCostRatio;

    public float getSafetyCostRatio() {
        return safetyCostRatio;
    }

    public void setSafetyCostRatio(float safetyCostRatio) {
        this.safetyCostRatio = safetyCostRatio;
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
