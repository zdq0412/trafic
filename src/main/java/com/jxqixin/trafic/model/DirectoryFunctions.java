package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * 目录菜单关联表
 */
@Entity
@Table(name="DIRECTORY_FUNCTIONS")
@GenericGenerator(name = "id_gen",strategy = "uuid")
public class DirectoryFunctions {
	private String id;
	private Functions functions;
	private Directory directory;
	@Id
	@GeneratedValue(generator = "id_gen")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="function_id",foreignKey= @ForeignKey(name="none",value=ConstraintMode.NO_CONSTRAINT))
	public Functions getFunctions() {
		return functions;
	}
	public void setFunctions(Functions functions) {
		this.functions = functions;
	}
	@ManyToOne
	@JoinColumn(name="directory_id",foreignKey= @ForeignKey(name="none",value=ConstraintMode.NO_CONSTRAINT))
	public Directory getDirectory() {
		return directory;
	}
	public void setDirectory(Directory directory) {
		this.directory = directory;
	}
}
