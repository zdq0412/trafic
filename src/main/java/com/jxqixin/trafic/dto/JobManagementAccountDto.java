package com.jxqixin.trafic.dto;

public class JobManagementAccountDto extends PageDto {
	private String id;
	/**名称*/
	private String name;
	/**创建日期*/
	private String createDate;
	/**文件名称*/
	private String filename;
	/**作业类别*/
	private String jobManagementAccountTypeId;
	/**上传日期*/
	private String uploadDate;
	/**备注*/
	private String note="";
	/**创建人*/
	private String creator;

	public String getJobManagementAccountTypeId() {
		return jobManagementAccountTypeId;
	}

	public void setJobManagementAccountTypeId(String jobManagementAccountTypeId) {
		this.jobManagementAccountTypeId = jobManagementAccountTypeId;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
}
