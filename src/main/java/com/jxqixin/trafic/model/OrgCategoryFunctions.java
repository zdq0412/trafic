package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ORG_CATEGORY_FUNCTIONS")
@GenericGenerator(name = "id_gen",strategy = "uuid")
public class OrgCategoryFunctions  implements Serializable {
	private String id;
	private Functions functions;
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
	@JoinColumn(name="function_id",foreignKey= @ForeignKey(name="none",value=ConstraintMode.NO_CONSTRAINT))
	public Functions getFunctions() {
		return functions;
	}
	public void setFunctions(Functions functions) {
		this.functions = functions;
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
