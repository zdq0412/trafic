package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Role implements Serializable{
	/**对象标识*/
	private String id;
	/**角色名称*/
	private String roleName;
	/**创建日期*/
	private Date createDate;
	/**备注*/
	private String note;
	/**是否允许删除*/
	private Boolean allowDelete=true;
	public Boolean getAllowDelete() {
		return allowDelete;
	}
	public void setAllowDelete(Boolean allowDelete) {
		this.allowDelete = allowDelete;
	}
	@Id
	@GeneratedValue(generator = "id_gen")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
