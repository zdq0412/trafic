package com.jxqixin.trafic.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 企业发文通知
 */
@Entity
@Table(name = "m006_notice")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Notice implements Serializable {
    @Id
    @GeneratedValue(generator = "id_gen")
    private String id;
    /**名称*/
    private String name;
    /**文件内容*/
    private String content;
    /**发布日期*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;
    /**发文部门名称*/
    private String publishDepartment;
    /**备注*/
    private String note;
    /**所属省*/
    @ManyToOne
    @JoinColumn(name="province_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private Category province;
    /**所属市*/
    @ManyToOne
    @JoinColumn(name="city_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private Category city;
    /**所属地区*/
    @ManyToOne
    @JoinColumn(name="region_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private Category region;
    /**企业类别名称*/
    @ManyToOne
    @JoinColumn(name = "orgCategory_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OrgCategory orgCategory;
    /**发文字号：企业简称+年+自增序号  自增序号为四位*/
    private String num;
    /**时效性：有效或无效*/
    private String timeliness;
    /**所属企业，如果没有企业，则所有企业都可见*/
    @ManyToOne
    @JoinColumn(name = "org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private Org org;
    @ManyToOne
    @JoinColumn(name = "law_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private Law law;
    @ManyToOne
    @JoinColumn(name = "rules_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private Rules rules;

    public Law getLaw() {
        return law;
    }

    public void setLaw(Law law) {
        this.law = law;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    public Category getProvince() {
        return province;
    }

    public void setProvince(Category province) {
        this.province = province;
    }

    public Category getCity() {
        return city;
    }

    public void setCity(Category city) {
        this.city = city;
    }

    public Category getRegion() {
        return region;
    }

    public void setRegion(Category region) {
        this.region = region;
    }

    public OrgCategory getOrgCategory() {
        return orgCategory;
    }

    public void setOrgCategory(OrgCategory orgCategory) {
        this.orgCategory = orgCategory;
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

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
}
