package com.jxqixin.trafic.dto;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 设备信息
 */
public class DeviceDto extends PageDto implements Serializable {
	private String id;
	/**名称*/
	private String name;
	/**设备编码*/
	private String equipmentCode;
	/**规格型号*/
	private String specification;
	/**生产厂商*/
	private String manufacturer;
	/**放置位置*/
	private String position;
	/**单价*/
	private BigDecimal price = new BigDecimal("0.00");
	/**购置日期*/
	private String buyDate;
	/**创建日期*/
	private String createDate;
	/**备注*/
	private String note;
	/**访问路径*/
	private String url;
	/**实际存储路径*/
	private String realPath;
	/**删除标识*/
	private Boolean deleted = false;
	/**设备类别ID*/
	private String categoryId;

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

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
