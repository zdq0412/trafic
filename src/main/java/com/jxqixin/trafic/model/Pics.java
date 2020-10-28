package com.jxqixin.trafic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 图片表：存储会议、培训、日常检查等现场图片
 */
@Entity
@Table(name="pics")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class Pics implements Serializable {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**图片路径*/
	private String url;
	/**真实路径，即存储路径*/
	private String realPath;
	/**图片所属类别:MEETING,TRAINING,CHECKING...*/
	private String type;
	/**所属类别记录ID*/
	private String pid;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
