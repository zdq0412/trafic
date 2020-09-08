package com.jxqixin.trafic.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 类别表，包括区域信息，如省、市、地区等
 */
@Entity
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Category implements Serializable {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**名称*/
	private String name;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**备注*/
	private String note;
	/**父类别*/
	@ManyToOne
	@JoinColumn(name = "pid",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Category parent;
	/**类别：如区域，性别，等级等*/
	private String type;
	/**删除标识*/
	private boolean deleted=false;
	/**
	 * 子类别
	 */
	@Transient
	@JsonIgnoreProperties(value={"parent"})
	private List<Category> children;

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
}
