package com.jxqixin.trafic.dto;

import java.io.Serializable;

/**
 * 设备维修保养检修记录
 */
public class DeviceMaintainDto extends PageDto implements Serializable {
	private String id;
	/**描述*/
	private String note;
	/**创建日期*/
	private String createDate;
	/**维修保养检修日期*/
	private String operDate;
	/**删除标识*/
	private Boolean deleted=false;
	private String deviceId;
	/**维修保养检修内容 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getOperDate() {
		return operDate;
	}

	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
