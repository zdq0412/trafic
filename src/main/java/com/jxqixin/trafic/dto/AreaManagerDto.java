package com.jxqixin.trafic.dto;

import com.jxqixin.trafic.model.OrgCategory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
public class AreaManagerDto {
	private String id;
	/**所属省*/
	private String province;
	/**所属市*/
	private String city;
	/**所属地区*/
	private String region;
	/**用户名*/
	private String username;
	/**企业类别id*/
	private String orgCategoryId;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrgCategoryId() {
		return orgCategoryId;
	}

	public void setOrgCategoryId(String orgCategoryId) {
		this.orgCategoryId = orgCategoryId;
	}
}
