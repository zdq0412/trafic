package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 危险货物运输企业安全生产隐患排查整改台账表
 */
@Entity
@Table(name = "m024_danger_goods_check")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class DangerGoodsCheck  implements Serializable {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**名称*/
	private String name;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**创建人*/
	private String creator;
	/**备注*/
	private String note;
	/**企业负责人签字*/
	private String orgPersonSign;
	/**安全检查人员签字*/
	private String securityPersonSign;
	/**签字文件访问路径*/
	private String url;
	/**签字文件真实存储路径*/
	private String realPath;
	@ManyToOne
	@JoinColumn(name="org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Org org;

	public String getId() {
		return id;
	}

	public String getOrgPersonSign() {
		return orgPersonSign;
	}

	public void setOrgPersonSign(String orgPersonSign) {
		this.orgPersonSign = orgPersonSign;
	}

	public String getSecurityPersonSign() {
		return securityPersonSign;
	}

	public void setSecurityPersonSign(String securityPersonSign) {
		this.securityPersonSign = securityPersonSign;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
}
