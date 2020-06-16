package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
@Entity
@Table(name="T_USER")
@GenericGenerator(name = "id_gen",strategy = "uuid")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	/**对象标识*/
	private String id;
	/**所属角色*/
	private Role role;
	/**用户名称*/
	private String username;
	/**密码*/
	private String password;
	/**真实姓名*/
	private String realName;
	/**联系方式*/
	private String tel;
	/**创建日期*/
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/**备注*/
	private String note;
	/**用户头像名称*/
	private String pic;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	@ManyToOne
	@JoinColumn(name = "ROLE_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
