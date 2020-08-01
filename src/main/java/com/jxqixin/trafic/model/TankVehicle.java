package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 危险货物道路运输罐式车辆罐体检查记录表
 */
@Entity
@Table(name = "m024_tank_vehicle")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class TankVehicle {
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
	private Boolean  checkItem1 = true;
	/**检查项，反光条是否完整、反光标示是否完整、反光牌是否有*/
	private Boolean checkItem2= true;
	/**检查项，罐后保险杠是否合格*/
	private Boolean checkItem3= true;
	/**检查项，静电接地带是否有效*/
	private Boolean checkItem4= true;
	/**检查项，罐体两边防护网是否完整*/
	private Boolean checkItem5= true;
	/**检查项，轮胎是否符合行车安全要求*/
	private Boolean checkItem6= true;
	/**检查项，灭火器是否合格*/
	private Boolean checkItem7= true;
	/**检查项，确认罐体上喷涂的介质名称是否与《公告》、《合格证》上记载的一致*/
	private Boolean checkItem8= true;
	/**检查项，喷涂的介质与记载的内容一致，运输介质属于国家安监总局等五部委文件《关于明确在用液体危险货物罐车加装紧急切断装置液体介质范围的通知》（安监总管三〔2014〕135号）中列举的17种介质范围。检查其卸料口处是否安装有紧急切断阀、紧急切断阀是否有远程控制系统。*/
	private Boolean checkItem9= true;
	/**检查项，检查紧急切断阀有无腐蚀、生锈、裂纹等缺陷，有无松脱、渗漏等现象，检查紧急切断阀控制按钮是否完好。*/
	private Boolean checkItem10= true;
	/**检查项，检查紧急切断阀是否处于关闭状态，没有关闭的要求当场关闭，并对驾驶人进行一次面对面的教育提示。*/
	private Boolean checkItem11= true;
	/**检查项，备用*/
	private Boolean checkItem12= true;
	/**检查项，备用*/
	private Boolean checkItem13= true;
	/**检查项，备用*/
	private Boolean checkItem14= true;
	/**检查项，备用*/
	private Boolean checkItem15;
	private String checkItem1Msg;
	private String checkItem2Msg;
	private String checkItem3Msg;
	private String checkItem4Msg;
	private String checkItem5Msg;
	private String checkItem6Msg;
	private String checkItem7Msg;
	private String checkItem8Msg;
	private String checkItem9Msg;
	private String checkItem10Msg;
	private String checkItem11Msg;
	private String checkItem12Msg;
	private String checkItem13Msg;
	private String checkItem14Msg;
	private String checkItem15Msg;
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
	@ManyToOne
	@JoinColumn(name="org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Org org;

	private String url;

	private String realPath;

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

	public Boolean getCheckItem1() {
		return checkItem1;
	}

	public void setCheckItem1(Boolean checkItem1) {
		this.checkItem1 = checkItem1;
	}

	public Boolean getCheckItem2() {
		return checkItem2;
	}

	public void setCheckItem2(Boolean checkItem2) {
		this.checkItem2 = checkItem2;
	}

	public Boolean getCheckItem3() {
		return checkItem3;
	}

	public void setCheckItem3(Boolean checkItem3) {
		this.checkItem3 = checkItem3;
	}

	public Boolean getCheckItem4() {
		return checkItem4;
	}

	public void setCheckItem4(Boolean checkItem4) {
		this.checkItem4 = checkItem4;
	}

	public Boolean getCheckItem5() {
		return checkItem5;
	}

	public void setCheckItem5(Boolean checkItem5) {
		this.checkItem5 = checkItem5;
	}

	public Boolean getCheckItem6() {
		return checkItem6;
	}

	public void setCheckItem6(Boolean checkItem6) {
		this.checkItem6 = checkItem6;
	}

	public Boolean getCheckItem7() {
		return checkItem7;
	}

	public void setCheckItem7(Boolean checkItem7) {
		this.checkItem7 = checkItem7;
	}

	public Boolean getCheckItem8() {
		return checkItem8;
	}

	public void setCheckItem8(Boolean checkItem8) {
		this.checkItem8 = checkItem8;
	}

	public Boolean getCheckItem9() {
		return checkItem9;
	}

	public void setCheckItem9(Boolean checkItem9) {
		this.checkItem9 = checkItem9;
	}

	public Boolean getCheckItem10() {
		return checkItem10;
	}

	public void setCheckItem10(Boolean checkItem10) {
		this.checkItem10 = checkItem10;
	}

	public Boolean getCheckItem11() {
		return checkItem11;
	}

	public void setCheckItem11(Boolean checkItem11) {
		this.checkItem11 = checkItem11;
	}

	public Boolean getCheckItem12() {
		return checkItem12;
	}

	public void setCheckItem12(Boolean checkItem12) {
		this.checkItem12 = checkItem12;
	}

	public Boolean getCheckItem13() {
		return checkItem13;
	}

	public void setCheckItem13(Boolean checkItem13) {
		this.checkItem13 = checkItem13;
	}

	public Boolean getCheckItem14() {
		return checkItem14;
	}

	public void setCheckItem14(Boolean checkItem14) {
		this.checkItem14 = checkItem14;
	}

	public Boolean getCheckItem15() {
		return checkItem15;
	}

	public void setCheckItem15(Boolean checkItem15) {
		this.checkItem15 = checkItem15;
	}

	public String getCheckItem1Msg() {
		return checkItem1Msg;
	}

	public void setCheckItem1Msg(String checkItem1Msg) {
		this.checkItem1Msg = checkItem1Msg;
	}

	public String getCheckItem2Msg() {
		return checkItem2Msg;
	}

	public void setCheckItem2Msg(String checkItem2Msg) {
		this.checkItem2Msg = checkItem2Msg;
	}

	public String getCheckItem3Msg() {
		return checkItem3Msg;
	}

	public void setCheckItem3Msg(String checkItem3Msg) {
		this.checkItem3Msg = checkItem3Msg;
	}

	public String getCheckItem4Msg() {
		return checkItem4Msg;
	}

	public void setCheckItem4Msg(String checkItem4Msg) {
		this.checkItem4Msg = checkItem4Msg;
	}

	public String getCheckItem5Msg() {
		return checkItem5Msg;
	}

	public void setCheckItem5Msg(String checkItem5Msg) {
		this.checkItem5Msg = checkItem5Msg;
	}

	public String getCheckItem6Msg() {
		return checkItem6Msg;
	}

	public void setCheckItem6Msg(String checkItem6Msg) {
		this.checkItem6Msg = checkItem6Msg;
	}

	public String getCheckItem7Msg() {
		return checkItem7Msg;
	}

	public void setCheckItem7Msg(String checkItem7Msg) {
		this.checkItem7Msg = checkItem7Msg;
	}

	public String getCheckItem8Msg() {
		return checkItem8Msg;
	}

	public void setCheckItem8Msg(String checkItem8Msg) {
		this.checkItem8Msg = checkItem8Msg;
	}

	public String getCheckItem9Msg() {
		return checkItem9Msg;
	}

	public void setCheckItem9Msg(String checkItem9Msg) {
		this.checkItem9Msg = checkItem9Msg;
	}

	public String getCheckItem10Msg() {
		return checkItem10Msg;
	}

	public void setCheckItem10Msg(String checkItem10Msg) {
		this.checkItem10Msg = checkItem10Msg;
	}

	public String getCheckItem11Msg() {
		return checkItem11Msg;
	}

	public void setCheckItem11Msg(String checkItem11Msg) {
		this.checkItem11Msg = checkItem11Msg;
	}

	public String getCheckItem12Msg() {
		return checkItem12Msg;
	}

	public void setCheckItem12Msg(String checkItem12Msg) {
		this.checkItem12Msg = checkItem12Msg;
	}

	public String getCheckItem13Msg() {
		return checkItem13Msg;
	}

	public void setCheckItem13Msg(String checkItem13Msg) {
		this.checkItem13Msg = checkItem13Msg;
	}

	public String getCheckItem14Msg() {
		return checkItem14Msg;
	}

	public void setCheckItem14Msg(String checkItem14Msg) {
		this.checkItem14Msg = checkItem14Msg;
	}

	public String getCheckItem15Msg() {
		return checkItem15Msg;
	}

	public void setCheckItem15Msg(String checkItem15Msg) {
		this.checkItem15Msg = checkItem15Msg;
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

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
}
