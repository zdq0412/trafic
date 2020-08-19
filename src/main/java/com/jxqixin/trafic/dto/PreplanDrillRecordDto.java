package com.jxqixin.trafic.dto;

/**
 * 应急救援预案演练记录表
 */
public class PreplanDrillRecordDto extends PageDto {
	private String id;
	/**名称*/
	private String name;
	/**应急预案类型：综合、专项、现场处置*/
	private String type;
	/**开展时间*/
	private String developDate;
	/**访问路径*/
	private String url;
	/**实际存储路径*/
	private String realPath;
	/**应急预案备案ID*/
	private String emergencyPlanBakId;
	/**备注*/
	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDevelopDate() {
		return developDate;
	}

	public void setDevelopDate(String developDate) {
		this.developDate = developDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getEmergencyPlanBakId() {
		return emergencyPlanBakId;
	}

	public void setEmergencyPlanBakId(String emergencyPlanBakId) {
		this.emergencyPlanBakId = emergencyPlanBakId;
	}
}
