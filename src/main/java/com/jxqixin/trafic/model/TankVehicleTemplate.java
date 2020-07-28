package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 危险货物道路运输罐式车辆罐体检查记录模板表
 */
@Entity
@Table(name = "m044_tank_vehicle_template")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class TankVehicleTemplate {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**名称*/
	private String name;
	/**车号*/
	private String carNo;
	/**处理意见*/
	private String suggestion;
	/**检查人员签字，至少两人*/
	private String checkPersonSign;
	/**检查项，罐体有无破损、罐体是否整洁、罐体灯光是否完整*/
	private String checkItem1;
	/**检查项，反光条是否完整、反光标示是否完整、反光牌是否有*/
	private String checkItem2;
	/**检查项，罐后保险杠是否合格*/
	private String checkItem3;
	/**检查项，静电接地带是否有效*/
	private String checkItem4;
	/**检查项，罐体两边防护网是否完整*/
	private String checkItem5;
	/**检查项，轮胎是否符合行车安全要求*/
	private String checkItem6;
	/**检查项，灭火器是否合格*/
	private String checkItem7;
	/**检查项，确认罐体上喷涂的介质名称是否与《公告》、《合格证》上记载的一致*/
	private String checkItem8;
	/**检查项，喷涂的介质与记载的内容一致，运输介质属于国家安监总局等五部委文件《关于明确在用液体危险货物罐车加装紧急切断装置液体介质范围的通知》（安监总管三〔2014〕135号）中列举的17种介质范围。检查其卸料口处是否安装有紧急切断阀、紧急切断阀是否有远程控制系统。*/
	private String checkItem9;
	/**检查项，检查紧急切断阀有无腐蚀、生锈、裂纹等缺陷，有无松脱、渗漏等现象，检查紧急切断阀控制按钮是否完好。*/
	private String checkItem10;
	/**检查项，检查紧急切断阀是否处于关闭状态，没有关闭的要求当场关闭，并对驾驶人进行一次面对面的教育提示。*/
	private String checkItem11;
	/**检查项，备用*/
	private String checkItem12;
	/**检查项，备用*/
	private String checkItem13;
	/**检查项，备用*/
	private String checkItem14;
	/**检查项，备用*/
	private String checkItem15;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**检查日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date checkDate;
	/**创建人*/
	private String creator;
	/**备注*/
	private String note;
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
	@ManyToOne
	@JoinColumn(name="org_category_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private OrgCategory orgCategory;

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

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getCheckPersonSign() {
		return checkPersonSign;
	}

	public void setCheckPersonSign(String checkPersonSign) {
		this.checkPersonSign = checkPersonSign;
	}

	public String getCheckItem1() {
		return checkItem1;
	}

	public void setCheckItem1(String checkItem1) {
		this.checkItem1 = checkItem1;
	}

	public String getCheckItem2() {
		return checkItem2;
	}

	public void setCheckItem2(String checkItem2) {
		this.checkItem2 = checkItem2;
	}

	public String getCheckItem3() {
		return checkItem3;
	}

	public void setCheckItem3(String checkItem3) {
		this.checkItem3 = checkItem3;
	}

	public String getCheckItem4() {
		return checkItem4;
	}

	public void setCheckItem4(String checkItem4) {
		this.checkItem4 = checkItem4;
	}

	public String getCheckItem5() {
		return checkItem5;
	}

	public void setCheckItem5(String checkItem5) {
		this.checkItem5 = checkItem5;
	}

	public String getCheckItem6() {
		return checkItem6;
	}

	public void setCheckItem6(String checkItem6) {
		this.checkItem6 = checkItem6;
	}

	public String getCheckItem7() {
		return checkItem7;
	}

	public void setCheckItem7(String checkItem7) {
		this.checkItem7 = checkItem7;
	}

	public String getCheckItem8() {
		return checkItem8;
	}

	public void setCheckItem8(String checkItem8) {
		this.checkItem8 = checkItem8;
	}

	public String getCheckItem9() {
		return checkItem9;
	}

	public void setCheckItem9(String checkItem9) {
		this.checkItem9 = checkItem9;
	}

	public String getCheckItem10() {
		return checkItem10;
	}

	public void setCheckItem10(String checkItem10) {
		this.checkItem10 = checkItem10;
	}

	public String getCheckItem11() {
		return checkItem11;
	}

	public void setCheckItem11(String checkItem11) {
		this.checkItem11 = checkItem11;
	}

	public String getCheckItem12() {
		return checkItem12;
	}

	public void setCheckItem12(String checkItem12) {
		this.checkItem12 = checkItem12;
	}

	public String getCheckItem13() {
		return checkItem13;
	}

	public void setCheckItem13(String checkItem13) {
		this.checkItem13 = checkItem13;
	}

	public String getCheckItem14() {
		return checkItem14;
	}

	public void setCheckItem14(String checkItem14) {
		this.checkItem14 = checkItem14;
	}

	public String getCheckItem15() {
		return checkItem15;
	}

	public void setCheckItem15(String checkItem15) {
		this.checkItem15 = checkItem15;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
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

	public OrgCategory getOrgCategory() {
		return orgCategory;
	}

	public void setOrgCategory(OrgCategory orgCategory) {
		this.orgCategory = orgCategory;
	}
}
