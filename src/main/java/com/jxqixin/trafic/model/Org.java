package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Org implements Serializable {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**组织机构代码*/
	private String code;
	/**组织机构名称*/
	private String name;
	/**联系人*/
	private String contact;
	/**联系方式*/
	private String tel;
	/**举报电话:默认和联系方式相同，企业用户可以修改*/
	@Column(name = "report_tel")
	private String reportTel;
	/**企业地址*/
	private String addr;
	/**法人*/
	private String legalPerson;
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
	private Category region;;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**状态，0：正常，1：禁用，2：删除*/
	private String status="0";
	/**企业描述*/
	private String note;
	@ManyToOne
	@JoinColumn(name="org_category_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private OrgCategory orgCategory;
	/**举报邮箱:企业邮箱*/
	private String email;
	/**企业介绍*/
	private String introduction;
	/**成立日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "established_time")
	private Date establishedTime;
	/**企业四色图访问路径*/
	private String fourColorPicUrl;
	/**企业四色图磁盘路径*/
	private String fourColorPicRealPath;
	/**
	 * 经营范围
	 */
	@Column(name = "business_scope")
	private String businessScope;
	/**简称*/
	private String shortName;

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public OrgCategory getOrgCategory() {
		return orgCategory;
	}

	public String getReportTel() {
		return reportTel;
	}

	public void setReportTel(String reportTel) {
		this.reportTel = reportTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFourColorPicUrl() {
		return fourColorPicUrl;
	}

	public void setFourColorPicUrl(String fourColorPicUrl) {
		this.fourColorPicUrl = fourColorPicUrl;
	}

	public String getFourColorPicRealPath() {
		return fourColorPicRealPath;
	}

	public void setFourColorPicRealPath(String fourColorPicRealPath) {
		this.fourColorPicRealPath = fourColorPicRealPath;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Date getEstablishedTime() {
		return establishedTime;
	}

	public void setEstablishedTime(Date establishedTime) {
		this.establishedTime = establishedTime;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public void setOrgCategory(OrgCategory orgCategory) {
		this.orgCategory = orgCategory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}
