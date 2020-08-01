package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 安全培训
 */
@Entity
@Table(name = "m022_training")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Training {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**名称*/
	private String name;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**培训名称*/
	private String trainingName;
	/**培训日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date trainingDate;
	/**培训地点*/
	private String trainingPlace;
	/**主持人*/
	private String president;
	/**记录人*/
	private String recorder;
	/**参加对象*/
	private String attendants;
	/**应到人数*/
	private Integer attendance;
	/**实到人数*/
	private Integer realAttendance;
	/**备注*/
	private String note;
	/**培训内容*/
	private String content;
	/**创建人*/
	private String creator;
	/**模板文件访问路径*/
	private String url;
	/**模板文件真实存储路径*/
	private String realPath;
	@ManyToOne
	@JoinColumn(name="org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Org org;

	public Integer getAttendance() {
		return attendance;
	}

	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}

	public Integer getRealAttendance() {
		return realAttendance;
	}

	public void setRealAttendance(Integer realAttendance) {
		this.realAttendance = realAttendance;
	}

	public Org getOrg() {
		return org;
	}

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

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public Date getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
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
