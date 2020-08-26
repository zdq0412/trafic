package com.jxqixin.trafic.dto;
/**
 * 会议或培训模板
 */
public class MeetingDto extends PageDto{
	private String id;
	/**名称*/
	private String name;
	/**备注*/
	private String note;
	/**模板文本内容*/
	private String content;
	/**会议名称*/
	private String meetingName;
	/**开会日期，年月日*/
	private String meetingDate;
	/**闭会日期，年月日*/
	private String endMeetingDate;
	/**会议地点*/
	private String meetingPlace;
	/**主持人*/
	private String president;
	/**记录人*/
	private String recorder;
	/**到场人员*/
	private String attendants;
	/**最后形成意见或决定*/
	private String finalDecision;

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

	public String getEndMeetingDate() {
		return endMeetingDate;
	}

	public void setEndMeetingDate(String endMeetingDate) {
		this.endMeetingDate = endMeetingDate;
	}

	public String getFinalDecision() {
		return finalDecision;
	}

	public void setFinalDecision(String finalDecision) {
		this.finalDecision = finalDecision;
	}
}
