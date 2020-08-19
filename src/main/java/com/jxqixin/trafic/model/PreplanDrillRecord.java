package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 应急救援预案演练记录表
 */
@Entity
@Table(name = "m038_preplan_drill_record")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class PreplanDrillRecord implements Serializable {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**名称*/
	private String name;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**应急预案类型：综合、专项、现场处置*/
	private String type;
	/**开展时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date developDate;
	/**访问路径*/
	private String url;
	/**实际存储路径*/
	private String realPath;
	/**备注*/
	private String note;
	@ManyToOne
	@JoinColumn(name = "emergency_plan_bak_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private EmergencyPlanBak emergencyPlanBak;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDevelopDate() {
		return developDate;
	}

	public void setDevelopDate(Date developDate) {
		this.developDate = developDate;
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

	public EmergencyPlanBak getEmergencyPlanBak() {
		return emergencyPlanBak;
	}

	public void setEmergencyPlanBak(EmergencyPlanBak emergencyPlanBak) {
		this.emergencyPlanBak = emergencyPlanBak;
	}
}
