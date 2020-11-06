package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="common_position_org_category")
@GenericGenerator(name = "id_gen",strategy = "uuid")
public class CommonPositionOrgCategory implements Serializable {
	private String id;
	private CommonPosition commonPosition;
	private OrgCategory orgCategory;
	@Id
	@GeneratedValue(generator = "id_gen")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="common_position_id",foreignKey= @ForeignKey(name="none",value=ConstraintMode.NO_CONSTRAINT))
	public CommonPosition getCommonPosition() {
		return commonPosition;
	}

	public void setCommonPosition(CommonPosition commonPosition) {
		this.commonPosition = commonPosition;
	}
	@ManyToOne
	@JoinColumn(name="org_category_id",foreignKey= @ForeignKey(name="none",value=ConstraintMode.NO_CONSTRAINT))
	public OrgCategory getOrgCategory() {
		return orgCategory;
	}

	public void setOrgCategory(OrgCategory orgCategory) {
		this.orgCategory = orgCategory;
	}
}
