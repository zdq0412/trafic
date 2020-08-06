package com.jxqixin.trafic.dto;

public class PositionDto extends PageDto {
	private String id;
	private String name;
	private String departmentId;
	private String note;
    private boolean managementLayer;

	public boolean isManagementLayer() {
		return managementLayer;
	}

	public void setManagementLayer(boolean managementLayer) {
		this.managementLayer = managementLayer;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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
}
