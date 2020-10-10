package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 过期提醒记录,企业资质、人员资质、设备档案和企业台账
 */
@Entity
@Table(name = "remind")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Remind implements Serializable {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**资源ID，即企业资质、人员资质、设备档案和企业台账等记录的id*/
	private String srcId;
	/**企业资质、人员证书、设备档案或企业台账的名称*/
	private String name;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**过期日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	/**
	 * 所属主体名称,如：企业名称、人员名称、企业台账名称或设备名称
	 */
	private String subjectName;
	/**企业ID*/
	private String orgId;
	/**企业名称*/
	private String orgName;
	/**提示背景颜色，警告：橙色FF9900，过期:红色FF0000*/
	private String bgColor;
	/**扣除的分数*/
	private int deductPoints=0;
	/**所属类别，台账：account,资质:qualifications,设备:device,人员：employee*/
	private String type;
	/**删除标识*/
	private boolean deleted=false;
	/**所属表名称*/
	private String tableName;
	/**日期或截止日期字段名称*/
	private String colName;

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public int getDeductPoints() {
		return deductPoints;
	}

	public void setDeductPoints(int deductPoints) {
		this.deductPoints = deductPoints;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
