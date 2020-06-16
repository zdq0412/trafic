package com.jxqixin.trafic.model;
import javax.persistence.*;
import java.util.Date;
@Entity
public class Power {
	/**访问的url*/
	private String url;
	/**权限名称*/
	private String powerName;
	/**创建日期*/
	private Date createDate = new Date();
	/**备注*/
	private String note;
	/**父权限*/
	private Power parent;
	/**类型:菜单或操作*/
	private String type;
	@ManyToOne
	@JoinColumn(name = "P_ID",referencedColumnName = "url",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	public Power getParent() {
		return parent;
	}

	public void setParent(Power parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPowerName() {
		return powerName;
	}
	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Id
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
