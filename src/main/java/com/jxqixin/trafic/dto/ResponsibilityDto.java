package com.jxqixin.trafic.dto;

import org.hibernate.annotations.GenericGenerator;

/**
 *企业文件
 */
@GenericGenerator(name="id_gen",strategy = "uuid")
public class ResponsibilityDto {
	private String id;
	/**名称*/
	private String name;
	/**备注*/
	private String note;
	/**文本内容*/
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
