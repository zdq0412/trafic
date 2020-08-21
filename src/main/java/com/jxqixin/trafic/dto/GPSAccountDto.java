package com.jxqixin.trafic.dto;

import java.io.Serializable;

/**
 * GPS使用日志,GPS台账
 */
public class GPSAccountDto extends PageDto implements Serializable{
	private String id;
	/**车牌号*/
	private String carNum;
	/**行驶日期*/
	private String driveDate;
	/**驾驶员姓名*/
	private String driverName;
	/**运营路线*/
	private String driveLines;
	/**货物名称*/
	private String goodsName;
	/**车辆运行状况*/
	private String carStatus;
	/**GPS运行状况*/
	private String gpsStatus;
	/**备注*/
	private String note;
	/**车辆有无违法违章行为*/
	private Boolean illegal;

	public Boolean getIllegal() {
		return illegal;
	}

	public void setIllegal(Boolean illegal) {
		this.illegal = illegal;
	}

	public String getDriveDate() {
		return driveDate;
	}

	public void setDriveDate(String driveDate) {
		this.driveDate = driveDate;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriveLines() {
		return driveLines;
	}

	public void setDriveLines(String driveLines) {
		this.driveLines = driveLines;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	public String getGpsStatus() {
		return gpsStatus;
	}

	public void setGpsStatus(String gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

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
}
