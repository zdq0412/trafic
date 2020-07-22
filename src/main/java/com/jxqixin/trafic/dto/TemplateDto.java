package com.jxqixin.trafic.dto;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 *企业文件模板
 */
@GenericGenerator(name="id_gen",strategy = "uuid")
public class TemplateDto {
	private String id;
	/**名称*/
	private String name;
	/**备注*/
	private String note;
	/**模板文本内容*/
	private String content;

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
