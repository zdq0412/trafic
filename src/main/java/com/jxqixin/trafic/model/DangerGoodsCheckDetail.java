package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 危险货物运输企业安全生产隐患排查整改台账模板详情表
 */
@Entity
@Table(name = "m049_danger_goods_check_detial")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class DangerGoodsCheckDetail {
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
	@ManyToOne
	@JoinColumn(name="danger_goods_check_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DangerGoodsCheckTemplate dangerGoodsCheckTemplate;
	/**销号时间*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cancelDate;

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

	public DangerGoodsCheckTemplate getDangerGoodsCheckTemplate() {
		return dangerGoodsCheckTemplate;
	}

	public void setDangerGoodsCheckTemplate(DangerGoodsCheckTemplate dangerGoodsCheckTemplate) {
		this.dangerGoodsCheckTemplate = dangerGoodsCheckTemplate;
	}
}
