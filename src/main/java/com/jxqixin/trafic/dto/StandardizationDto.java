package com.jxqixin.trafic.dto;

public class StandardizationDto extends PageDto {
	private String id;
	/**名称*/
	private String name;
	/**创建日期*/
	private String createDate;
	/**文件名称*/
	private String filename;
	/**上传日期*/
	private String uploadDate;
	/**有效期开始日期*/
	private String beginDate;
	/**有效期结束日期*/
	private String endDate;
	/**备注*/
	private String note;
	/**创建人*/
	private String creator;
	/**文件访问路径*/
	private String url;
	/**文件真实存储路径*/
	private String realPath;

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

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
