package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 安全会议 安全培训 通过type字段区分
 */
@Entity
@Table(name = "m021_meeting")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Meeting {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**名称*/
	private String name;
	/**类别 meeting:安全会议,training:安全培训*/
	private String type;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**会议名称*/
	private String meetingName;
	/**开会日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date meetingDate;
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
	/**备注*/
	private String note;
	/**会议或培训内容*/
	private String content;
	/**创建人*/
	private String creator;
	/**上传文件访问路径*/
	private String url;
	/**实际存储路径*/
	private String realPath;
	/**所属企业*/
	@ManyToOne
	@JoinColumn(name="org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Org org;

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

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

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

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
}
