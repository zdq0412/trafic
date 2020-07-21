package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;
/**
 * 区域管理员
 */
@Entity
@GenericGenerator(name="id_gen",strategy = "uuid")
@Table(name="area_manager")
public class AreaManager {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**所属省*/
	@ManyToOne
	@JoinColumn(name="province_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Category province;
	/**所属市*/
	@ManyToOne
	@JoinColumn(name="city_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Category city;
	/**所属地区*/
	@ManyToOne
	@JoinColumn(name="region_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Category region;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**用户名：用户名不能与T_USER表中的用户名同名*/
	private String username;
	/**密码*/
	private String password;
	/**企业所属类别*/
	@ManyToOne
	@JoinColumn(name = "org_category_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private OrgCategory orgCategory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public OrgCategory getOrgCategory() {
		return orgCategory;
	}

	public void setOrgCategory(OrgCategory orgCategory) {
		this.orgCategory = orgCategory;
	}

	public Category getProvince() {
		return province;
	}

	public void setProvince(Category province) {
		this.province = province;
	}

	public Category getCity() {
		return city;
	}

	public void setCity(Category city) {
		this.city = city;
	}

	public Category getRegion() {
		return region;
	}

	public void setRegion(Category region) {
		this.region = region;
	}
}
