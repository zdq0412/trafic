package com.jxqixin.trafic.dto;
/**
 * 应急预案备案
 */
public class EmergencyPlanBakDto extends PageDto{
	private String id;
	/**预案名称*/
	private String prePlanName;
	private String prePlanUploadDate;
	/**预案文件访问路径*/
	private String prePlanUrl;
	/**预案实际存储路径*/
	private String prePlanRealPath;
	/**备案名称*/
	private String keepOnRecordName;
	/**备案上传日期*/
	private String keepOnRecordUploadDate;
	/**备案文件访问路径*/
	private String keepOnRecordUrl;
	/**备案实际存储路径*/
	private String keepOnRecordRealPath;
	/**所属企业*/
	private String orgId;
	/**预案编制日期*/
	private String writeDate;
	/**备案日期*/
	private String keepOnRecordDate;

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getKeepOnRecordDate() {
		return keepOnRecordDate;
	}

	public void setKeepOnRecordDate(String keepOnRecordDate) {
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

	public String getPrePlanUploadDate() {
		return prePlanUploadDate;
	}

	public void setPrePlanUploadDate(String prePlanUploadDate) {
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

	public String getKeepOnRecordUploadDate() {
		return keepOnRecordUploadDate;
	}

	public void setKeepOnRecordUploadDate(String keepOnRecordUploadDate) {
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
