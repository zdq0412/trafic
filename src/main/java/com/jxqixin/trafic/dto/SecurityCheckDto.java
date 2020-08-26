package com.jxqixin.trafic.dto;

/**
 * 安全检查
 */
public class SecurityCheckDto extends PageDto{
	private String id;
	/**名称*/
	private String name;
	/**检查对象*/
	private String checkObject;
	/**监督检查的部门及人员*/
	private String deptAndEmp;
	/**检查的内容*/
	private String content;
	/**提出的问题*/
	private String problems;
	/**整改结果*/
	private String result;
	/**整改结果确认人签字*/
	private String confirmerSign;
	/**检查人员签字,以后可能为签字图片路径*/
	private String supervisorsSign;
	/**受检查对象签字,以后可能为签字图片路径*/
	private String checkedObjectSign;
	/**安全检查日期*/
	private String checkDate;
	/**备注*/
	private String note;

	public String getConfirmerSign() {
		return confirmerSign;
	}

	public void setConfirmerSign(String confirmerSign) {
		this.confirmerSign = confirmerSign;
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

	public String getCheckObject() {
		return checkObject;
	}

	public void setCheckObject(String checkObject) {
		this.checkObject = checkObject;
	}

	public String getDeptAndEmp() {
		return deptAndEmp;
	}

	public void setDeptAndEmp(String deptAndEmp) {
		this.deptAndEmp = deptAndEmp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProblems() {
		return problems;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSupervisorsSign() {
		return supervisorsSign;
	}

	public void setSupervisorsSign(String supervisorsSign) {
		this.supervisorsSign = supervisorsSign;
	}

	public String getCheckedObjectSign() {
		return checkedObjectSign;
	}

	public void setCheckedObjectSign(String checkedObjectSign) {
		this.checkedObjectSign = checkedObjectSign;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
