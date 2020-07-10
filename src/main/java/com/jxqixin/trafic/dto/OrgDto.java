package com.jxqixin.trafic.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class OrgDto {
	private String id;
	/**组织机构代码*/
	private String code;
	/**组织机构名称*/
	private String name;
	/**联系人*/
	private String contact;
	/**联系方式*/
	private String tel;
	/**安全举报电话*/
	private String reportTel;
	/**企业地址*/
	private String addr;
	/**法人*/
	private String legalPerson;
	/**所属省*/
	private String province;
	/**所属市*/
	private String city;
	/**所属地区*/
	private String region;
	/**企业描述*/
	private String note;
	/**企业所属类别id*/
	private String orgCategoryId;
	/**安全举报邮箱*/
	private String email;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date establishedTime;
	/**经营范围*/
	private String businessScope;
	/**企业介绍*/
	private String introduction;

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOrgCategoryId() {
		return orgCategoryId;
	}

	public void setOrgCategoryId(String orgCategoryId) {
		this.orgCategoryId = orgCategoryId;
	}
}
