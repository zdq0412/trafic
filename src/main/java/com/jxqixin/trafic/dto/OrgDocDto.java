package com.jxqixin.trafic.dto;

import com.jxqixin.trafic.model.Org;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 企业资质文件
 */
@GenericGenerator(name="id_gen",strategy = "uuid")
public class OrgDocDto implements Serializable {
	private String id;
	/**名称*/
	private String name;
	/**文件编号*/
	private String docNum;
	/**有效期起始日期*/
	private String beginDate;
	/**有效期截止日期*/
	private String endDate;

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

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
