package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 危险货物运输企业安全生产隐患排查整改台账详情表
 */
@Entity
@Table(name = "m024_danger_goods_check_detial_record")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class DangerGoodsCheckDetailRecord  implements Serializable {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**被检查单位*/
	private String checkedOrg;
	/**存在的安全隐患*/
	private String hiddenDanger;
	/**整改措施*/
	private String correctiveAction;
	/**整改时限*/
	private String timelimit;
	/**责任人*/
	private String person;
	/**详情中的备注*/
	private String detailNote;
	/**整改到位时间*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	/**检查日期*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;
	/*@ManyToOne
	@JoinColumn(name="danger_goods_check_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DangerGoodsCheck dangerGoodsCheck;*/
	/**销号时间*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cancelDate;
	/**是否已整改*/
	private Boolean rectification=false;
	/**整改金额*/
	private BigDecimal rectificationFund = new BigDecimal("0.00");
	/**严重程度*/
	@ManyToOne
	@JoinColumn(name="severity_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Category severity;
	/**隐患原因类别:人的不安全行为，物的不安全状态，管理上的缺失，环境不良等*/
	@ManyToOne
	@JoinColumn(name="reason_category_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Category reasonCategory;
	/**所在企业*/
	@ManyToOne
	@JoinColumn(name="org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Org org;

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Boolean getRectification() {
		return rectification;
	}

	public void setRectification(Boolean rectification) {
		this.rectification = rectification;
	}

	public BigDecimal getRectificationFund() {
		return rectificationFund;
	}

	public void setRectificationFund(BigDecimal rectificationFund) {
		this.rectificationFund = rectificationFund;
	}

	public Category getSeverity() {
		return severity;
	}

	public void setSeverity(Category severity) {
		this.severity = severity;
	}

	public Category getReasonCategory() {
		return reasonCategory;
	}

	public void setReasonCategory(Category reasonCategory) {
		this.reasonCategory = reasonCategory;
	}

	public String getDetailNote() {
		return detailNote;
	}

	public void setDetailNote(String detailNote) {
		this.detailNote = detailNote;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCheckedOrg() {
		return checkedOrg;
	}

	public void setCheckedOrg(String checkedOrg) {
		this.checkedOrg = checkedOrg;
	}

	public String getHiddenDanger() {
		return hiddenDanger;
	}

	public void setHiddenDanger(String hiddenDanger) {
		this.hiddenDanger = hiddenDanger;
	}

	public String getCorrectiveAction() {
		return correctiveAction;
	}

	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}

	public String getTimelimit() {
		return timelimit;
	}

	public void setTimelimit(String timelimit) {
		this.timelimit = timelimit;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
}
