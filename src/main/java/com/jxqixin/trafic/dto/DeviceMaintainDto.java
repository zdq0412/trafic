package com.jxqixin.trafic.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
	private String deviceId;

	private BigDecimal price;
	/**维修保养检修内容 */
	private String content;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
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
