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
	/**正常状态*/
	public static final String OK="0";
	/**禁用状态*/
	public static final String DISABLED="1";
	/**删除状态*/
	public static final String DELETED="2";
	/**对象标识*/
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**所属角色*/
	@ManyToOne
	@JoinColumn(name = "role_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Role role;
	/**用户名*/
	private String username;
	/**密码*/
	private String password;
	/**真实姓名*/
	private String realname;
	/**联系方式*/
	private String tel;
	/**状态，0：正常，1：禁用，2：删除*/
	private String status="0";
	/**是否允许删除*/
	private boolean allowedDelete=true;
	/**创建人*/
	private String creator;
	/**用户头像访问路径*/
	private String photo;
	/**用户头像实际存储路径*/
	private String realpath;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**备注*/
	private String note;
	/**所属企业*/
	@ManyToOne
	@JoinColumn(name = "org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Org org;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getRealpath() {
		return realpath;
	}

	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isAllowedDelete() {
		return allowedDelete;
	}

	public void setAllowedDelete(boolean allowedDelete) {
		this.allowedDelete = allowedDelete;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

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

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
}
