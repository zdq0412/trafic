package com.jxqixin.trafic.dto;
public class CommonPositionDto extends PageDto {
	private String id;
	/**名称*/
	private String name;
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
}
