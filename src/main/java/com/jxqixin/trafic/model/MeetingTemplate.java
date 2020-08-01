package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;
/**
 * 安全会议模板表
 */
@Entity
@Table(name = "m046_meeting_template")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class MeetingTemplate {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**名称*/
	private String name;
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
	/**闭会日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endMeetingDate;
	/**会议地点*/
	private String meetingPlace;
	/**主持人*/
	private String president;
	/**记录人*/
	private String recorder;
	/**出席人*/
	private String attendants;
	/**最后形成意见或决定*/
	private String finalDecision;
	/**备注*/
	private String note;
	/**会议内容*/
	private String content;
	/**创建人*/
	private String creator;
	/**模板文件访问路径*/
	private String url;
	/**模板文件真实存储路径*/
	private String realPath;
	/**所属省*/
	@ManyToOne
	@JoinColumn(name="province_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Category province;
	/**所属市*/
	@ManyToOne
	@JoinColumn(name="city_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Category city;
	/**所属地区*/
	@ManyToOne
	@JoinColumn(name="region_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Category region;
	@ManyToOne
	@JoinColumn(name="org_category_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private OrgCategory orgCategory;
	@ManyToOne
	@JoinColumn(name="org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Org org;

	public Date getEndMeetingDate() {
		return endMeetingDate;
	}

	public void setEndMeetingDate(Date endMeetingDate) {
		this.endMeetingDate = endMeetingDate;
	}

	public String getFinalDecision() {
		return finalDecision;
	}

	public void setFinalDecision(String finalDecision) {
		this.finalDecision = finalDecision;
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
	public Category getProvince() {
		return province;
	}

	public void setProvince(Category province) {
		this.province = province;
	}

	public Category getCity() {
		return city;
	}

	public void setCity(Category city) {
		this.city = city;
	}

	public Category getRegion() {
		return region;
	}

	public void setRegion(Category region) {
		this.region = region;
	}

	public OrgCategory getOrgCategory() {
		return orgCategory;
	}

	public void setOrgCategory(OrgCategory orgCategory) {
		this.orgCategory = orgCategory;
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
