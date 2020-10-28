package com.jxqixin.trafic.dto;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 设备档案
 */
public class DeviceArchiveDto extends PageDto implements Serializable {
	private String id;
	/**档案名称*/
	private String name;
	/**描述*/
	private String note;
	/**有效期开始*/
	private String beginDate;
	/**有效期截止*/
	private String endDate;
	/**所属设备ID*/
	private String deviceId;
	/**金额*/
	private BigDecimal money;
	/**供应方*/
	private String supplier;

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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
