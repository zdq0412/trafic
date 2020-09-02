package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 应急预案备案
 */
@Entity
@Table(name = "m038_emergency_plan_bak")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class EmergencyPlanBak  implements Serializable {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**预案名称*/
	private String prePlanName;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**预案编制日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date writeDate;
	/**备案日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date keepOnRecordDate;
	/**预案上传日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date prePlanUploadDate;
	/**预案文件访问路径*/
	private String prePlanUrl;
	/**预案实际存储路径*/
	private String prePlanRealPath;
	/**备案名称*/
	private String keepOnRecordName;
	/**是否已备案*/
	private Boolean keepOnRecord = false;
	/**备案上传日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date keepOnRecordUploadDate;
	/**备案文件访问路径*/
	private String keepOnRecordUrl;
	/**备案实际存储路径*/
	private String keepOnRecordRealPath;
	/**所属企业*/
	@ManyToOne
	@JoinColumn(name = "org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Org org;

	public Boolean getKeepOnRecord() {
		return keepOnRecord;
	}

	public void setKeepOnRecord(Boolean keepOnRecord) {
		this.keepOnRecord = keepOnRecord;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public Date getKeepOnRecordDate() {
		return keepOnRecordDate;
	}

	public void setKeepOnRecordDate(Date keepOnRecordDate) {
		this.keepOnRecordDate = keepOnRecordDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrePlanName() {
		return prePlanName;
	}

	public void setPrePlanName(String prePlanName) {
		this.prePlanName = prePlanName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getPrePlanUploadDate() {
		return prePlanUploadDate;
	}

	public void setPrePlanUploadDate(Date prePlanUploadDate) {
		this.prePlanUploadDate = prePlanUploadDate;
	}

	public String getPrePlanUrl() {
		return prePlanUrl;
	}

	public void setPrePlanUrl(String prePlanUrl) {
		this.prePlanUrl = prePlanUrl;
	}

	public String getPrePlanRealPath() {
		return prePlanRealPath;
	}

	public void setPrePlanRealPath(String prePlanRealPath) {
		this.prePlanRealPath = prePlanRealPath;
	}

	public String getKeepOnRecordName() {
		return keepOnRecordName;
	}

	public void setKeepOnRecordName(String keepOnRecordName) {
		this.keepOnRecordName = keepOnRecordName;
	}

	public Date getKeepOnRecordUploadDate() {
		return keepOnRecordUploadDate;
	}

	public void setKeepOnRecordUploadDate(Date keepOnRecordUploadDate) {
		this.keepOnRecordUploadDate = keepOnRecordUploadDate;
	}

	public String getKeepOnRecordUrl() {
		return keepOnRecordUrl;
	}

	public void setKeepOnRecordUrl(String keepOnRecordUrl) {
		this.keepOnRecordUrl = keepOnRecordUrl;
	}

	public String getKeepOnRecordRealPath() {
		return keepOnRecordRealPath;
	}

	public void setKeepOnRecordRealPath(String keepOnRecordRealPath) {
		this.keepOnRecordRealPath = keepOnRecordRealPath;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
}
