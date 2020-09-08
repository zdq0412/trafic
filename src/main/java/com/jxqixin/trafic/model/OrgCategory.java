package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * 企业类别
 */
@Entity
@Table(name = "org_category")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class OrgCategory implements Serializable {
    @Id
    @GeneratedValue(generator = "id_gen")
    private String id;
    /**企业类别名称*/
    private String name;
    /**类别描述*/
    private String note;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    /**删除标识*/
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 安全生产费用提取百分比
     */

    private Float safetyCostRatio;
    public Float getSafetyCostRatio() {
        return safetyCostRatio;
    }
    public void setSafetyCostRatio(Float safetyCostRatio) {
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
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
