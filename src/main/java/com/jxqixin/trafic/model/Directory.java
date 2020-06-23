package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
/**
 * 目录，相当于一级菜单，目录下是二级以下子菜单信息
 */
@Entity
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Directory {
	@Id
	//@GeneratedValue(generator = "id_gen")
	private String id;
	/**权限名称*/
	private String name;
	/**图标*/
	private String icon;
	/**唯一标识，要和前端路由的地址相同*/
	@Column(name = "c_index")
	private String index;
	/**优先级，值越大页面显示越靠前*/
	private int priority;
	/**状态，0：正常，1：禁用，2：删除*/
	private String status;
	/**创建人*/
	private String creator;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**备注*/
	private String note;
	/**所属模式*/
	@ManyToOne
	@JoinColumn(name = "schema_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Schema schema;

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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIndex() {
		return index==null?"":index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}
}
