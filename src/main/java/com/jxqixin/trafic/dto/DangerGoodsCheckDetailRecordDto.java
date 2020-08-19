package com.jxqixin.trafic.dto;

import java.math.BigDecimal;

/**
 * 危险货物运输企业安全生产隐患排查整改台账详情表
 */
public class DangerGoodsCheckDetailRecordDto extends PageDto{
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
	private String endTime;
	/**检查日期*/
	private String checkDate;
	/**检查日期开始，用于根据检查日期范围查找*/
	private String checkDateFrom;
	/**检查日期截止，用于根据检查日期范围查找*/
	private String checkDateTo;
	/**销号时间*/
	private String cancelDate;
	/**是否已整改*/
	private Boolean rectification=false;
	/**整改金额*/
	private BigDecimal rectificationFund = new BigDecimal("0.00");
	/**严重程度*/
	private String severityId;
	/**隐患原因类别:人的不安全行为，物的不安全状态，管理上的缺失，环境不良等*/
	private String reasonCategoryId;

	public String getCheckDateFrom() {
		return checkDateFrom;
	}

	public void setCheckDateFrom(String checkDateFrom) {
		this.checkDateFrom = checkDateFrom;
	}

	public String getCheckDateTo() {
		return checkDateTo;
	}

	public void setCheckDateTo(String checkDateTo) {
		this.checkDateTo = checkDateTo;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getSeverityId() {
		return severityId;
	}

	public void setSeverityId(String severityId) {
		this.severityId = severityId;
	}

	public String getReasonCategoryId() {
		return reasonCategoryId;
	}

	public void setReasonCategoryId(String reasonCategoryId) {
		this.reasonCategoryId = reasonCategoryId;
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
}
