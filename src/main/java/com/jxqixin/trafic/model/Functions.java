package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;
/**
 * 权限表
 */
@Entity
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Functions {
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
	/**访问路径*/
	private String url;
	/**是否为叶子节点*/
	private boolean leaf;
	/**权限类型：功能或菜单  1:菜单  0：功能*/
	private String type;
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
	/**父类别*/
	@ManyToOne
	@JoinColumn(name = "pid",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Functions parent;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Functions getParent() {
		return parent;
	}

	public void setParent(Functions parent) {
		this.parent = parent;
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
}
