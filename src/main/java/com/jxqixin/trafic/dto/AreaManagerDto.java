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
	private String provinceId;
	/**所属市*/
	private String cityId;
	/**所属地区*/
	private String regionId;
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
