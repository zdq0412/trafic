package com.jxqixin.trafic.dto;
/**
 * 会议或培训模板
 */
public class MeetingTemplateDto {
	private String id;
	/**名称*/
	private String name;
	/**备注*/
	private String note;
	/**模板文本内容*/
	private String content;
	/**类别 meeting:安全会议,training:安全培训*/
	private String type;
	/**会议名称*/
	private String meetingName;
	/**开会日期，年月日*/
	private String meetingDate;
	/**会议地点*/
	private String meetingPlace;
	/**主持人*/
	private String president;
	/**记录人*/
	private String recorder;
	/**到场人员*/
	private String attendants;
	/**到场人数*/
	private int attendance;
	/**缺席人数*/
	private int absent;
	/**会议主题*/
	private String theme;
	/**需解决的问题或反馈的问题*/
	private String problems;
	/**解决方法*/
	private String methods;
	/**模板备注*/
	private String templateNote;
	private String provinceId;
	private String cityId;
	private String regionId;
	private String orgCategoryId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getMeetingPlace() {
		return meetingPlace;
	}

	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

	public String getPresident() {
		return president;
	}

	public void setPresident(String president) {
		this.president = president;
	}

	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	public String getAttendants() {
		return attendants;
	}

	public void setAttendants(String attendants) {
		this.attendants = attendants;
	}

	public int getAttendance() {
		return attendance;
	}

	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}

	public int getAbsent() {
		return absent;
	}

	public void setAbsent(int absent) {
		this.absent = absent;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getProblems() {
		return problems;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public String getMethods() {
		return methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public String getTemplateNote() {
		return templateNote;
	}

	public void setTemplateNote(String templateNote) {
		this.templateNote = templateNote;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getOrgCategoryId() {
		return orgCategoryId;
	}

	public void setOrgCategoryId(String orgCategoryId) {
		this.orgCategoryId = orgCategoryId;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
