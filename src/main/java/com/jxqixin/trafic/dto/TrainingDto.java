package com.jxqixin.trafic.dto;
/**
 * 培训模板
 */
public class TrainingDto {
	private String id;
	/**名称*/
	private String name;
	/**备注*/
	private String note;
	/**模板文本内容*/
	private String content;
	/**培训名称*/
	private String trainingName;
	/**开会日期，年月日*/
	private String trainingDate;
	/**培训地点*/
	private String trainingPlace;
	/**主持人*/
	private String president;
	/**记录人*/
	private String recorder;
	/**参加对象*/
	private String attendants;
	/**应到人数*/
	private int attendance;
	/**实到人数*/
	private int realAttendance;
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

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public String getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(String trainingDate) {
		this.trainingDate = trainingDate;
	}

	public String getTrainingPlace() {
		return trainingPlace;
	}

	public void setTrainingPlace(String trainingPlace) {
		this.trainingPlace = trainingPlace;
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

	public int getRealAttendance() {
		return realAttendance;
	}

	public void setRealAttendance(int realAttendance) {
		this.realAttendance = realAttendance;
	}
}
