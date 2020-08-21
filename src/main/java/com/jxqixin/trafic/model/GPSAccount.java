package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * GPS使用日志,GPS台账
 */
@Entity
@Table(name="m020_gps_account")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class GPSAccount implements Serializable{
	/**对象标识*/
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**车牌号*/
	private String carNum;
	/**创建日期*/
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/**行驶日期*/
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date driveDate;
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
	/**车辆有无违法违章行为*/
	private Boolean illegal = false;
	/**备注*/
	private String note;
	/**所属企业*/
	@ManyToOne
	@JoinColumn(name = "org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Org org;

	public Boolean getIllegal() {
		return illegal;
	}

	public void setIllegal(Boolean illegal) {
		this.illegal = illegal;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDriveDate() {
		return driveDate;
	}

	public void setDriveDate(Date driveDate) {
		this.driveDate = driveDate;
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

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
